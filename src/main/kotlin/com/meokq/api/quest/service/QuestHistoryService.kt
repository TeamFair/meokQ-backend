package com.meokq.api.quest.service

import com.meokq.api.core.JpaService
import com.meokq.api.quest.model.QuestHistory
import com.meokq.api.quest.repository.QuestHistoryRepository
import com.meokq.api.quest.response.QuestHistoryCreateResp
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service

@Service
class QuestHistoryService(
    private val repository : QuestHistoryRepository,

) : JpaService<QuestHistory, String>{
    override var jpaRepository: JpaRepository<QuestHistory, String> = repository

    fun save(questId: String, customerId: String ) : QuestHistoryCreateResp{
        val questHistory = QuestHistory(questId = questId, customerId = customerId)
        val model = saveModel(questHistory)
        return QuestHistoryCreateResp(model)
    }

    fun findByCustomerId(customerId: String, pageable: Pageable) : Page<QuestHistory> {
        return repository.findByCustomerId(customerId, pageable)
    }
}