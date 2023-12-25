package com.meokq.api.market.service

import com.meokq.api.core.converter.BaseConverter
import com.meokq.api.core.exception.NotFoundException
import com.meokq.api.core.service.BaseService
import com.meokq.api.market.converter.MarketAuthConverter
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

    /**
     * 마켓의 등록 여부를 검증
     * 1. 마켓인증 내역 수정한다.
     * 2. 마켓 상태를 수정한다.
     */
    fun reviewMarketAuth(request: MarketAuthReviewReq) {
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
}