package com.meokq.api.challenge.service

import com.meokq.api.answer.model.AnswerHistoryEntity
import com.meokq.api.auth.request.AuthReq
import com.meokq.api.challenge.enums.ChallengeStatus
import com.meokq.api.challenge.model.Challenge
import com.meokq.api.challenge.repository.ChallengeRepository
import com.meokq.api.challenge.request.ChallengeQuizReq
import com.meokq.api.challenge.request.ChallengeQuizSearchDto
import com.meokq.api.challenge.response.ReadChallengeQuizResp
import com.meokq.api.challenge.response.ReadChallengeRespForQueryDSL
import com.meokq.api.quest.repository.MissionRepository
import com.meokq.api.quest.repository.QuestRepository
import com.meokq.api.quiz.repository.QuizRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class ChallengeQuizService (
    val challengeRepository: ChallengeRepository,
    val missionRepository: MissionRepository,
    val quizRepository: QuizRepository,
){

    /**
     * 퀴즈 타입의 챌린지를 생성합니다.
     */
    @Transactional
    fun createQuizChallenge(req: ChallengeQuizReq, authReq: AuthReq) {
        val quizList = req.answers.map { it.quizId }
        val missionList = req.answers.map { it.missionId }

        val quizMap = quizRepository.findAllById(quizList).associateBy { it.quizId }
        val missionMap = missionRepository.findAllById(missionList).associateBy { it.missionId }

        // 정답 채점하기
        // 정답만 등록할수 있음.
        for (q in quizList) {
            val quiz = quizMap[q] ?: throw IllegalArgumentException("퀴즈를 찾을 수 없습니다.")
            val submitAnswer = req.answers.find { it.quizId == quiz.quizId }?.answer
            if (submitAnswer == null) {
                throw IllegalArgumentException("정답을 찾을 수 없습니다.")
            }
            if (quiz.answers.none { it.content == submitAnswer }) {
                throw IllegalArgumentException("정답이 아닙니다.")
            }
        }

        val challenge = Challenge(
            status = ChallengeStatus.UNDER_REVIEW, // 초기 상태 : under_review
            questId = req.questId,
            customerId = authReq.userId,
            answers = req.answers.map { AnswerHistoryEntity(
                content = it.answer,
                mission = missionMap[it.missionId] ?: throw IllegalArgumentException("미션을 찾을 수 없습니다."),
                quiz = quizMap[it.quizId] ?: throw IllegalArgumentException("퀴즈를 찾을 수 없습니다.")
            ) }.toMutableList()
        )

        challengeRepository.save(challenge)
    }

    /**
     * 퀴즈 타입의 챌린지를 조회합니다.
     */
    fun quizChallengeList(dto: ChallengeQuizSearchDto, userId: String?, pageable: PageRequest): PageImpl<ReadChallengeQuizResp> {
        return challengeRepository.getChallengeQuizList(dto, userId, pageable)
    }
}