package com.meokq.api.xp

import com.meokq.api.core.JpaService
import com.meokq.api.core.JpaSpecificationService
import com.meokq.api.core.repository.BaseRepository
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
}