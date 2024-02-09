package com.meokq.api.user.service

import com.meokq.api.auth.enums.UserType
import com.meokq.api.auth.request.AuthReq
import com.meokq.api.core.JpaService
import com.meokq.api.core.JpaSpecificationService
import com.meokq.api.core.repository.BaseRepository
import com.meokq.api.user.model.Agreement
import com.meokq.api.user.repository.AgreementRepository
import com.meokq.api.user.request.AgreementReq
import com.meokq.api.user.request.AgreementSearchDto
import com.meokq.api.user.response.AgreementResp
import com.meokq.api.user.specification.AgreementSpecification
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
        val count = countBy(specification)
        val responses = models.map { AgreementResp(it) } // TODO : Data Convert 는 추후 변경 고려
        return PageImpl(responses, pageable, count)
    }

    fun saveAll(authReq: AuthReq, reqList: List<AgreementReq>){
        reqList.forEach { it.userId = authReq.userId }
        val models = reqList.map { Agreement(it) } // TODO : Data Convert 는 추후 변경 고려
        saveModels(models)
    }

}