package com.meokq.api.coupon.service

import com.meokq.api.auth.enums.UserType
import com.meokq.api.auth.request.AuthReq
import com.meokq.api.core.DataValidation.checkNotNullData
import com.meokq.api.core.JpaService
import com.meokq.api.core.JpaSpecificationService
import com.meokq.api.core.exception.InvalidRequestException
import com.meokq.api.core.repository.BaseRepository
import com.meokq.api.coupon.enums.CouponStatus
import com.meokq.api.coupon.model.Coupon
import com.meokq.api.coupon.repository.CouponRepository
import com.meokq.api.coupon.request.CouponSaveReq
import com.meokq.api.coupon.request.CouponSearchReq
import com.meokq.api.coupon.response.CouponResp
import com.meokq.api.coupon.specification.CouponSpec
import com.meokq.api.quest.service.RewardService
import com.meokq.api.user.service.CustomerService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class CouponService(
    private val repository: CouponRepository,
    private val customerService: CustomerService,
    private val rewardService: RewardService,
) : JpaService<Coupon, String>, JpaSpecificationService<Coupon, String> {

    override var jpaRepository: JpaRepository<Coupon, String> = repository
    override val jpaSpecRepository: BaseRepository<Coupon, String> = repository
    private val specifications = CouponSpec

    fun findAll(
        searchDto: CouponSearchReq,
        pageable: Pageable,
        authReq: AuthReq,
    ): Page<CouponResp> {
        if (authReq.userType == UserType.BOSS)
            checkNotNullData(searchDto.marketId, "마켓아이디가 없습니다.")
        else if (authReq.userType == UserType.CUSTOMER && searchDto.userDataOnly == true){
            searchDto.userId = authReq.userId
        }

        val specification = specifications.bySearchDto(searchDto)
        val models = findAllBy(specification = specification, pageable = pageable)
        val count = countBy(specification)
        val responses = models.map {
            val nickname = it.userId?.let { userId -> customerService.findModelById(userId).nickname }
            val reward = it.rewardId?.let { it1 -> rewardService.findModelById(it1) }
            CouponResp(it, nickname, reward)
        } // TODO : Data Convert 는 추후 변경 고려
        return PageImpl(responses, pageable, count)
    }

    fun saveAll(request: CouponSaveReq) : List<Coupon> {
        checkNotNullData(request.questId, "연결된 퀘스트 아이디가 없습니다.")

        val rewards = rewardService.findModelsByQuestId(request.questId!!)
        val modelsToSave = mutableListOf<Coupon>()
        rewards.forEach {
            modelsToSave.add(
                Coupon(
                    challengeId = request.challengeId,
                    questId = request.questId,
                    rewardId = it.rewardId,
                    marketId = request.marketId,
                    userId = request.userId,
                )
            )
        }

        return saveModels(modelsToSave)
    }

    fun useCoupon(couponId : String, authReq: AuthReq) {
        val model = findModelById(couponId)

        if (model.userId != authReq.userId)
            throw InvalidRequestException("쿠폰의 소유자와 로그인 유저가 다릅니다.")
        if (!model.status.couldUse)
            throw InvalidRequestException("사용할 수 없는 쿠폰 상태상태입니다.")

        model.status = CouponStatus.USED
        model.useDate = LocalDateTime.now()
        repository.save(model)
    }
}