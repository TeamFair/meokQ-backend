package com.meokq.api.quiz.service

import com.meokq.api.core.JpaService
import com.meokq.api.core.exception.NotFoundException
import com.meokq.api.quest.model.Mission
import com.meokq.api.quest.repository.MissionRepository
import com.meokq.api.quest.request.MissionReq
import com.meokq.api.quest.response.MissionResp
import com.meokq.api.quiz.model.QuizEntity
import com.meokq.api.quiz.repository.QuizRepository
import com.meokq.api.quiz.response.QuizResp
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class QuizService(
    val repository: QuizRepository,
): JpaService<QuizEntity, String> {

    override var jpaRepository: JpaRepository<QuizEntity, String> = repository

    fun findForRandom(missionId: String): QuizResp {
        val quiz = this.repository.findRandomByMissionId(missionId)
            ?: throw NotFoundException("quiz is not found by missionId : $missionId")

        return QuizResp(quiz)
    }

}
