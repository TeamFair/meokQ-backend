package com.meokq.api.agreement.service

import com.meokq.api.agreement.model.Agreement
import com.meokq.api.agreement.repository.AgreementRepository
import com.meokq.api.agreement.request.AgreementReq
import com.meokq.api.agreement.request.AgreementSearchDto
import com.meokq.api.agreement.response.AgreementResp
import com.meokq.api.agreement.specification.AgreementSpecification
import com.meokq.api.auth.enums.UserType
import com.meokq.api.auth.request.AuthReq
import com.meokq.api.core.JpaService
import com.meokq.api.core.JpaSpecificationService
import com.meokq.api.core.repository.BaseRepository
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service

@Service
class AgreementService(
    private val repository : AgreementRepository,
) : JpaService<Agreement, String>, JpaSpecificationService<Agreement, String> {

    override var jpaRepository: JpaRepository<Agreement, String> = repository
    override val jpaSpecRepository: BaseRepository<Agreement, String> = repository
    private val specifications = AgreementSpecification

    fun findAll(authReq: AuthReq, searchDto: AgreementSearchDto, pageable: Pageable): PageImpl<AgreementResp> {
        searchDto.userId = if (authReq.userType != UserType.ADMIN) authReq.userId else null
        val specification = specifications.bySearchDto(searchDto)
        val models = findAllBy(specification = specification, pageable = pageable)
        val responses = models.content.map(::AgreementResp) // TODO : Data Convert 는 추후 변경 고려
        return PageImpl(responses, pageable, models.totalElements)
    }

    fun saveAll(authReq: AuthReq, reqList: List<AgreementReq>){
        reqList.forEach {
            it.userId = authReq.userId
            it.agreementType.checkTarget(authReq.userType)
        }

        val models = reqList.map(::Agreement) // TODO : Data Convert 는 추후 변경 고려
        saveModels(models)
    }

}