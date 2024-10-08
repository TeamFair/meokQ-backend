package com.meokq.api.xp.service

import com.meokq.api.core.JpaService
import com.meokq.api.core.JpaSpecificationService
import com.meokq.api.core.repository.BaseRepository
import com.meokq.api.xp.dto.response.XpHisResp
import com.meokq.api.xp.dto.request.XpSearchDto
import com.meokq.api.xp.model.XpHistory
import com.meokq.api.xp.processor.UserAction
import com.meokq.api.xp.repository.XpHisRepository
import com.meokq.api.xp.repository.XpHisSpecification
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class XpHistoryService(
    private val repository: XpHisRepository,

    ): JpaService<XpHistory, String>, JpaSpecificationService<XpHistory, String> {

    override var jpaRepository: JpaRepository<XpHistory, String> = repository
    override val jpaSpecRepository: BaseRepository<XpHistory, String> = repository
    private val specifications = XpHisSpecification

    fun findAll(searchDto: XpSearchDto, pageable: Pageable): PageImpl<XpHisResp> {
        val specification = specifications.bySearchDto(searchDto)
        val models = findAllBy(specification, pageable)
        val responses = models.content.map { XpHisResp(it) }
        return PageImpl(responses, pageable, models.totalElements)
    }

    fun writeHistory(userAction: UserAction, userId: String): XpHisResp {
        val model = XpHistory(userAction = userAction, userId = userId,)
        saveModel(model)
        return XpHisResp(model)
    }

    fun withdrawHistory(userAction: UserAction, userId: String) {
        writeHistory(userAction.xpCustomer(userAction.xpType!!,-userAction.xpPoint),userId)
    }


}