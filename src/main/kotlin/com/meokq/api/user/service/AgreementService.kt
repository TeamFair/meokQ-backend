package com.meokq.api.user.service

import com.meokq.api.core.converter.BaseConverter
import com.meokq.api.core.service.BaseService
import com.meokq.api.user.converter.AgreementConverter
import com.meokq.api.user.model.Agreement
import com.meokq.api.user.repository.AgreementRepository
import com.meokq.api.user.request.AgreementReq
import com.meokq.api.user.request.AgreementSearchDto
import com.meokq.api.user.response.AgreementResp
import com.meokq.api.user.specification.AgreementSpecification
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service

@Service
class AgreementService(
    private val repository : AgreementRepository,
    private val converter : AgreementConverter,
) : BaseService<AgreementReq, AgreementResp, Agreement, String> {
    override var _repository: JpaRepository<Agreement, String> = repository
    override var _converter: BaseConverter<AgreementReq, AgreementResp, Agreement> = converter

    fun findAll(searchDto: AgreementSearchDto, pageable: Pageable): PageImpl<AgreementResp> {
        val pageableWithSorting = PageRequest.of(
            pageable.pageNumber, pageable.pageSize, Sort.by("createDate").descending()
        )

        val specification = AgreementSpecification.bySearchDto(searchDto)
        val page = repository.findAll(specification, pageableWithSorting)

        val content = converter.modelToResponse(page.content)
        return PageImpl(content, pageable, page.numberOfElements.toLong())
    }
}