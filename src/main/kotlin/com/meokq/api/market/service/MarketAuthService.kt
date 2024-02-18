package com.meokq.api.market.service

import com.meokq.api.core.converter.BaseConverter
import com.meokq.api.core.exception.InvalidRequestException
import com.meokq.api.core.exception.NotFoundException
import com.meokq.api.core.service.BaseService
import com.meokq.api.market.converter.MarketAuthConverter
import com.meokq.api.market.enums.MarketStatus
import com.meokq.api.market.model.MarketAuth
import com.meokq.api.market.repository.MarketAuthRepository
import com.meokq.api.market.reposone.MarketAuthResp
import com.meokq.api.market.request.MarketAuthReq
import com.meokq.api.market.request.MarketAuthReviewReq
import com.meokq.api.market.request.MarketAuthSearchDto
import com.meokq.api.market.specification.MarketAuthSpecification
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service

@Service
class MarketAuthService(
    private val repository: MarketAuthRepository,
    private val converter: MarketAuthConverter,
    private val marketService: MarketService,
) : BaseService<MarketAuthReq, MarketAuthResp, MarketAuth, String> {
    override var _repository: JpaRepository<MarketAuth, String> = repository
    override var _converter: BaseConverter<MarketAuthReq, MarketAuthResp, MarketAuth> = converter

    fun findAll(
        searchDto: MarketAuthSearchDto,
        pageable: Pageable,
    ): PageImpl<MarketAuthResp> {

        val pageableWithSorting = getBasePageableWithSorting(pageable)
        val specification = MarketAuthSpecification.bySearchDto(searchDto)
        val page = repository.findAll(specification, pageableWithSorting)
        val content = page.content.map{ MarketAuthResp(it) }

        val count = repository.count(specification)
        return PageImpl(content, pageable, count)
    }

    /**
     * 마켓의 등록 여부를 검증
     * 1. 마켓인증 내역 수정한다.
     * 2. 마켓 상태를 수정한다.
     */
    fun submitReview(request: MarketAuthReviewReq) {
        // update market-auth(result)
        checkNotNullData(request.recordId, "인증내역 식별자가 없습니다.")

        val marketAuth = repository.findById(request.recordId)
            .orElseThrow { NotFoundException("Market Auth record not found with ID: ${request.recordId}") }
        marketAuth.reviewResult = request.reviewResult
        marketAuth.comment = request.comment
        repository.save(marketAuth)

        // update market(status)
        checkNotNullData(marketAuth.marketId, "마켓 아이디가 없습니다.")
        marketService.updateMarketStatus(marketAuth.marketId!!, request.reviewResult.status)
    }

    fun requestReview(marketId: String) {
        // TODO : 유효한 인증내역이 있는지 확인
        // TODO : MarketService 의 updateMarketStatus 를 이용하는 것을 고려
        // TODO : MarketStatus 별 제약조건을 Enum 으로 관리하는 것을 고려
        // 유효한 마켓 정보가 있는지 확인
        val market = marketService.findModelById(marketId)
        if (market.status != MarketStatus.REGISTERED)
            throw InvalidRequestException("${MarketStatus.REGISTERED.name} 상태일때만 마켓 인증정보 검토를 요청할 수 있습니다. (현재 마켓상태 : ${market.status})")

        // 마켓 상태 수정
        market.status = MarketStatus.UNDER_REVIEW
        marketService.saveModel(market)
    }
}