package com.meokq.api.xp.service

import com.meokq.api.core.JpaService
import com.meokq.api.core.JpaSpecificationService
import com.meokq.api.core.model.TargetMetadata
import com.meokq.api.core.repository.BaseRepository
import com.meokq.api.xp.dto.XpHisResp
import com.meokq.api.xp.dto.XpSearchDto
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

    @Transactional
    fun save(userAction: UserAction, targetMetadata: TargetMetadata): XpHisResp{
        val result = saveModel(XpHistory(
            xpPoint = userAction.xpPoint,
            title = userAction.title,
            targetMetadata = targetMetadata))
        return XpHisResp(result)
    }

    fun deleteByTargetMetadata(targetMetadata: TargetMetadata) {
        repository.deleteByTargetIdAndUserId(targetMetadata.targetId, targetMetadata.userId)
    }

    fun deleteByTargetId(targetId:String) : List<XpHistory> {
        val xpHistory = repository.findAllByTargetId(targetId)
        repository.deleteByTargetId(targetId)
        return xpHistory

    }

}