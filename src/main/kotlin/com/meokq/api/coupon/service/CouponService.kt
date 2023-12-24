package com.meokq.api.coupon.service

import com.meokq.api.auth.enums.UserType
import com.meokq.api.auth.request.AuthReq
import com.meokq.api.challenge.service.ChallengeService
import com.meokq.api.core.converter.BaseConverter
import com.meokq.api.core.exception.InvalidRequestException
import com.meokq.api.core.exception.NotFoundException
import com.meokq.api.core.service.BaseService
import com.meokq.api.coupon.converter.CouponConverter
import com.meokq.api.coupon.enums.CouponStatus
import com.meokq.api.coupon.model.Coupon
import com.meokq.api.coupon.repository.CouponRepository
import com.meokq.api.coupon.request.CouponReq
import com.meokq.api.coupon.request.CouponSaveReq
import com.meokq.api.coupon.request.CouponSearchReq
import com.meokq.api.coupon.response.CouponResp
import com.meokq.api.coupon.specification.CouponSpec
import com.meokq.api.quest.service.MissionService
import com.meokq.api.quest.service.RewardService
import com.meokq.api.user.service.CustomerService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service

@Service
class CouponService(
    private val converter: CouponConverter,
    private val repository: CouponRepository,
    private val challengeService: ChallengeService,
    private val customerService: CustomerService,
    private val rewardService: RewardService,
    private val missionService: MissionService,

    ) : BaseService<CouponReq, CouponResp, Coupon, String>{
    override var _converter: BaseConverter<CouponReq, CouponResp, Coupon> = converter
    override var _repository: JpaRepository<Coupon, String> = repository

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

        val pageableWithSorting = getBasePageableWithSorting(pageable)
        val page = repository.findAll(CouponSpec.bySearchDto(searchDto), pageableWithSorting)
        val content = page.content.map{
            val couponResp = converter.modelToResponse(it)
            try {
                couponResp.userNickname = it.userId?.let { it1 -> customerService.findModelById(it1).nickname }
                couponResp.reward = it.rewardId?.let { it1 -> rewardService.findById(it1) }
                couponResp.missions = it.questId?.let { it1 -> missionService.findAllByQuestId(it1) }
            } catch (e : NotFoundException){
                throw e
            }
            couponResp
        }

        return PageImpl(content, pageable, page.numberOfElements.toLong())
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

        return repository.saveAll(modelsToSave)
    }

    fun useCoupon(couponId : String) {
        val model = findModelById(couponId)
        if (!model.status.couldUse) throw InvalidRequestException("사용할 수 없는 쿠폰 상태상태입니다.")
        model.status = CouponStatus.USED
        repository.save(model)
    }
}