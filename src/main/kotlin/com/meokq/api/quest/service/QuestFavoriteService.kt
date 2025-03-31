package com.meokq.api.quest.service

import com.meokq.api.auth.request.AuthReq
import com.meokq.api.challenge.service.ChallengeService
import com.meokq.api.core.JpaService
import com.meokq.api.core.JpaSpecificationService
import com.meokq.api.core.repository.BaseRepository
import com.meokq.api.quest.enums.QuestTarget
import com.meokq.api.quest.enums.QuestType
import com.meokq.api.quest.model.Quest
import com.meokq.api.quest.model.QuestFavorite
import com.meokq.api.quest.repository.QuestFavoriteRepository
import com.meokq.api.quest.repository.QuestHistoryRepository
import com.meokq.api.quest.repository.QuestRepository
import com.meokq.api.quest.repository.queryDSL.QuestCustomRepositoryImpl
import com.meokq.api.quest.request.QuestCreateReq
import com.meokq.api.quest.request.QuestCreateReqForAdmin
import com.meokq.api.quest.request.QuestSearchDto
import com.meokq.api.quest.request.QuestUpdateReq
import com.meokq.api.quest.response.*
import com.meokq.api.quest.specification.QuestSpecification
import com.meokq.api.quiz.repository.QuizRepository
import com.meokq.api.quiz.response.QuizResp
import jakarta.persistence.EntityManager
import lombok.RequiredArgsConstructor
import org.springframework.data.domain.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
class QuestFavoriteService(
    private val repository: QuestFavoriteRepository,

    ) : JpaService<QuestFavorite, String>, JpaSpecificationService<QuestFavorite, String> {
    override var jpaRepository: JpaRepository<QuestFavorite, String> = repository
    override val jpaSpecRepository: BaseRepository<QuestFavorite, String> = repository

    fun findByCustomerIdAndQuests(questIds: List<String>, customerId: String): List<QuestFavorite> {
        return this.repository.findAllByQuestIdInAndCustomerId(questIds, customerId)
    }

    @Transactional
    fun save(questId: String, authReq: AuthReq): QuestFavorite {
        return repository.save(QuestFavorite(questId = questId, customerId = authReq.userId!!))
    }

    @Transactional
    fun delete(questId: String, authReq: AuthReq) {
        repository.deleteByQuestIdAndCustomerId(questId, authReq.userId!!)
    }

}
