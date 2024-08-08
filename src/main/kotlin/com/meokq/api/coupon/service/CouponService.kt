package com.meokq.api.coupon.service

import com.meokq.api.auth.enums.UserType
import com.meokq.api.auth.request.AuthReq
import com.meokq.api.core.DataValidation.checkNotNullData
import com.meokq.api.core.JpaService
import com.meokq.api.core.JpaSpecificationService
import com.meokq.api.core.exception.DataException
import com.meokq.api.core.exception.InvalidRequestException
import com.meokq.api.core.repository.BaseRepository
import com.meokq.api.coupon.enums.CouponStatus
import com.meokq.api.coupon.model.Coupon
import com.meokq.api.coupon.repository.CouponRepository
import com.meokq.api.coupon.request.CouponSaveReq
import com.meokq.api.coupon.request.CouponSearchReq
import com.meokq.api.coupon.response.CouponDetailResp
import com.meokq.api.coupon.specification.CouponSpec
import com.meokq.api.quest.model.Mission
import com.meokq.api.quest.model.Reward
import com.meokq.api.quest.service.MissionService
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
    private val missionService: MissionService,
) : JpaService<Coupon, String>, JpaSpecificationService<Coupon, String> {

    override var jpaRepository: JpaRepository<Coupon, String> = repository
    override val jpaSpecRepository: BaseRepository<Coupon, String> = repository
    private val specifications = CouponSpec

    fun findAll(
        searchDto: CouponSearchReq,
        pageable: Pageable,
        authReq: AuthReq,
    ): Page<CouponDetailResp> {
        if (authReq.userType == UserType.BOSS)
            checkNotNullData(searchDto.marketId, "마켓아이디가 없습니다.")
        else if (authReq.userType == UserType.CUSTOMER && searchDto.userDataOnly == true){
            searchDto.userId = authReq.userId
        }

        val specification = specifications.bySearchDto(searchDto)
        val models = findAllBy(specification = specification, pageable = pageable)
        val responses = models.content.map {convertModelToResp(it)}
        return PageImpl(responses, pageable, models.totalElements)
    }

    private fun convertModelToResp(coupon: Coupon) : CouponDetailResp{
        var nickname: String? = null
        var reward: Reward? = null
        var missions: List<Mission>? = listOf()

        try {
            nickname = coupon.userId?.let { userId -> customerService.findModelById(userId).nickname }

        } catch (e: DataException){
            // TODO : 커스텀 쿼리로 변환을 고려.
        }

        try {
            reward = coupon.rewardId?.let { rewardId -> rewardService.findModelById(rewardId) }

        } catch (e: DataException){
            // TODO : 커스텀 쿼리로 변환을 고려.
        }

        try {
            missions = coupon.questId?.let { questId -> missionService.findModelsByQuestId(questId) }

        } catch (e: DataException){
            // TODO : 커스텀 쿼리로 변환을 고려.
        }

        return CouponDetailResp(coupon, nickname, reward, missions)
    }

    fun saveAll(reqList: List<CouponSaveReq>) : List<Coupon> {
        val models = reqList.map(::Coupon)
        val respList = repository.saveAll(models)
        return respList
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

    fun findAllByChallengeId(challengeId: String): List<Coupon> {
        return repository.findAllByChallengeId(challengeId)
    }
}