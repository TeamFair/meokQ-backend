package com.meokq.api.challenge.service

import com.meokq.api.answer.AnswerRepository
import com.meokq.api.answer.model.AnswerHistoryEntity
import com.meokq.api.auth.request.AuthReq
import com.meokq.api.challenge.enums.ChallengeStatus
import com.meokq.api.challenge.model.Challenge
import com.meokq.api.challenge.repository.ChallengeQuizDsl
import com.meokq.api.challenge.repository.ChallengeRepository
import com.meokq.api.challenge.request.ChallengeQuizReq
import com.meokq.api.challenge.request.ChallengeQuizSearchDto
import com.meokq.api.challenge.response.CreateChallengeResp
import com.meokq.api.challenge.response.ReadChallengeQuizResp
import com.meokq.api.quiz.repository.QuizRepository
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class ChallengeQuizService (
    val challengeRepository: ChallengeRepository,
    val quizRepository: QuizRepository,
    val answerRepository: AnswerRepository,
    val challengeQuizDsl: ChallengeQuizDsl
) {

    /**
     * 퀴즈 타입의 챌린지를 생성합니다.
     */
    @Transactional
    fun createQuizChallenge(req: ChallengeQuizReq, authReq: AuthReq): CreateChallengeResp {
        val quizList = req.answers.map { it.quizId }.distinct()

        // 퀴즈, 정답 한 번씩만 조회
        val quizMap = quizRepository.findAllById(quizList).associateBy { it.quizId }
        val answerMap = answerRepository.findByQuizQuizIdIn(quizList)
            .groupBy { it.quiz.quizId } // 하나의 퀴즈에 여러 개의 정답이 있을 수 있음.

        // 정답 검증
        req.answers.forEach { submittedAnswer ->
            val quiz = quizMap[submittedAnswer.quizId]
                ?: throw IllegalArgumentException("퀴즈를 찾을 수 없습니다.")
            val correctAnswers = answerMap[submittedAnswer.quizId]?.map { it.content } ?: emptyList()

            if (correctAnswers.isEmpty() || submittedAnswer.answer !in correctAnswers) {
                throw IllegalArgumentException("정답이 아닙니다.")
            }
        }

        // Challenge 생성
        val challenge = Challenge(
            status = ChallengeStatus.APPROVED, // 초기 설정 APPROVED
            questId = req.questId,
            customerId = authReq.userId,
            answers = req.answers.map {
                val quiz = quizMap[it.quizId] ?: throw IllegalArgumentException("퀴즈를 찾을 수 없습니다.")

                AnswerHistoryEntity(
                    content = it.answer,
                    quiz = quiz
                )
            }.toMutableList()
        )

        // Challenge 저장
        val savedChallenge = challengeRepository.save(challenge)

        return CreateChallengeResp(savedChallenge)
    }

    /**
     * 퀴즈 타입의 챌린지를 조회합니다.
     */
    fun quizChallengeList(dto: ChallengeQuizSearchDto, userId: String?, pageable: PageRequest): PageImpl<ReadChallengeQuizResp> {
        return challengeQuizDsl.getChallengeQuizList(dto, userId, pageable)
    }
}