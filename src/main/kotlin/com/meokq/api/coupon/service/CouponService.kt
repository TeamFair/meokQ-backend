package com.meokq.api.coupon.service

import com.meokq.api.challenge.service.ChallengeService
import com.meokq.api.core.converter.BaseConverter
import com.meokq.api.core.exception.NotFoundException
import com.meokq.api.core.service.BaseService
import com.meokq.api.coupon.converter.CouponConverter
import com.meokq.api.coupon.model.Coupon
import com.meokq.api.coupon.repository.CouponRepository
import com.meokq.api.coupon.request.CouponReq
import com.meokq.api.coupon.request.CouponSearchReq
import com.meokq.api.coupon.response.CouponResp
import com.meokq.api.coupon.specification.CouponSpec
import org.springframework.data.domain.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service

@Service
class CouponService(
    private val converter: CouponConverter,
    private val repository: CouponRepository,
    private val challengeService: ChallengeService,

    ) : BaseService<CouponReq, CouponResp, Coupon, String>{
    override var _converter: BaseConverter<CouponReq, CouponResp, Coupon> = converter
    override var _repository: JpaRepository<Coupon, String> = repository

    fun findAll(searchDto: CouponSearchReq, pageable: Pageable): Page<CouponResp> {
        val pageableWithSorting = PageRequest.of(
            pageable.pageNumber, pageable.pageSize, Sort.by("createDate").descending()
        )
        val page = repository.findAll(CouponSpec.bySearchDto(searchDto), pageableWithSorting)
        val content = page.content.map{
            val couponResp = converter.modelToResponse(it)
            try {
                val challenge = challengeService.findById(it.challengeId?:throw NotFoundException("challenge id is null!"))
                couponResp.quest = challenge.quest
            } catch (e : NotFoundException){
                throw e
            }
            couponResp
        }

        return PageImpl(content, pageable, page.numberOfElements.toLong())
    }
}