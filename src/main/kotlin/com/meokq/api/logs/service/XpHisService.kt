package com.meokq.api.logs.service

import com.meokq.api.core.JpaService
import com.meokq.api.core.JpaSpecificationService
import com.meokq.api.core.exception.NotFoundException
import com.meokq.api.core.repository.BaseRepository
import com.meokq.api.logs.dto.XpHisResp
import com.meokq.api.logs.dto.XpSearchDto
import com.meokq.api.logs.model.XpHistory
import com.meokq.api.logs.repository.XpHisRepository
import com.meokq.api.logs.repository.XpHisSpecification
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service

@Service
class XpHisService(
    private val repository: XpHisRepository,
): JpaService<XpHistory, String>, JpaSpecificationService<XpHistory, String> {
    override var jpaRepository: JpaRepository<XpHistory, String> = repository
    override val jpaSpecRepository: BaseRepository<XpHistory, String> = repository
    private val specifications = XpHisSpecification

    fun findAll(searchDto: XpSearchDto, pageable: Pageable): PageImpl<XpHisResp> {
        val specification = specifications.bySearchDto(searchDto)
        val models = findAllBy(specification, pageable)
        val responses = models.map { XpHisResp(it) }

        val count = countBy(specification)
        return PageImpl(responses, pageable, count)
    }

    fun deleteByTitle(targetId: String) {
        repository.deleteByTitle(targetId)
    }
}