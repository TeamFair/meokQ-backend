package com.meokq.api.challenge.service

import com.meokq.api.answer.AnswerRepository
import com.meokq.api.answer.model.AnswerEntity
import com.meokq.api.answer.model.AnswerHistoryEntity
import com.meokq.api.auth.enums.AuthChannel
import com.meokq.api.auth.enums.UserType
import com.meokq.api.auth.request.AuthReq
import com.meokq.api.auth.request.LoginReq
import com.meokq.api.auth.service.AuthService
import com.meokq.api.challenge.enums.ChallengeStatus
import com.meokq.api.challenge.repository.ChallengeRepository
import com.meokq.api.challenge.request.AnswerHistoryReq
import com.meokq.api.challenge.request.ChallengeQuizReq
import com.meokq.api.challenge.response.CreateChallengeResp
import com.meokq.api.core.exception.NotFoundException
import com.meokq.api.quest.enums.MissionType
import com.meokq.api.quest.enums.QuestTarget
import com.meokq.api.quest.enums.QuestType
import com.meokq.api.quest.enums.RewardType
import com.meokq.api.quest.model.Quest
import com.meokq.api.quest.repository.MissionRepository
import com.meokq.api.quest.request.AnswerReq
import com.meokq.api.quest.request.MissionReq
import com.meokq.api.quest.request.QuestCreateReqForAdmin
import com.meokq.api.quest.request.RewardReq
import com.meokq.api.quest.service.QuestService
import com.meokq.api.quiz.model.QuizEntity
import com.meokq.api.quiz.repository.QuizRepository
import com.meokq.api.quiz.request.QuizReq
import com.meokq.api.quiz.service.QuizService
import com.meokq.api.user.response.UserResp
import com.meokq.api.user.service.CustomerService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional

@Transactional
@SpringBootTest
@ActiveProfiles("local")
internal class ChallengeQuizServiceTest {
    @Autowired
    private lateinit var missionRepository: MissionRepository

    @Autowired
    private lateinit var challengeRepository: ChallengeRepository
    @Autowired
    private lateinit var quizRepository: QuizRepository
    @Autowired
    private lateinit var answerRepository: AnswerRepository
    @Autowired
    private lateinit var questService: QuestService
    @Autowired
    private lateinit var quizService: QuizService
    @Autowired
    private lateinit var challengeQuizService: ChallengeQuizService
    @Autowired
    private lateinit var challengeService: ChallengeService
    @Autowired
    private lateinit var authService: AuthService
    @Autowired
    private lateinit var customerService: CustomerService

    @BeforeEach
    fun setUp() {
        challengeRepository.deleteAllInBatch()
    }


    @Test
    @DisplayName("퀴즈 타입의 챌린지를 생성한다.")
    fun createQuizChallenge() {
        // Given
        val quest = saveQuest()
        val mission = missionRepository.findAllByQuestId(quest.questId!!)


        val quiz = quizRepository.save(QuizEntity(question = "테스트 문제", hint = "힌트", mission = mission.first()))
        val answer = answerRepository.save(AnswerEntity(content = "정답", quiz = quiz))

        val submittedAnswers = listOf(AnswerHistoryReq(quizId = quiz.quizId!!, answer = "정답"))
        val req = ChallengeQuizReq(questId = "quest-123", answers = submittedAnswers)
        val authReq = AuthReq(userId = "user-1")

        // When
        val response: CreateChallengeResp = challengeQuizService.createQuizChallenge(req, authReq)

        // Then
        assertNotNull(response)

        // 저장된 Challenge 데이터 확인
        val savedChallenge = challengeRepository.findById(response.challengeId!!).orElse(null)
        assertNotNull(savedChallenge)
        assertEquals(savedChallenge?.questId, "quest-123")
        assertEquals(savedChallenge?.customerId, "user-1")
        assertEquals(savedChallenge?.status, ChallengeStatus.APPROVED)

        // Challenge에 저장된 AnswerHistory 확인
        val answerHistories: List<AnswerHistoryEntity> = savedChallenge?.answers ?: emptyList()
        assertFalse(answerHistories.isEmpty())
        assertEquals(answerHistories.first().content, "정답")
        assertEquals(answerHistories.first().quiz?.quizId, quiz.quizId)
    }

    @Test
    @DisplayName("퀴즈 타입의 챌린지를 삭제한다.")
    fun deleteQuizChallenge() {
        // Given
        val quest = saveQuest()
        val mission = missionRepository.findAllByQuestId(quest.questId!!)
        val quiz = quizRepository.save(QuizEntity(question = "테스트 문제", hint = "힌트", mission = mission.first()))
        val answer = answerRepository.save(AnswerEntity(content = "정답", quiz = quiz))

        val submittedAnswers = listOf(AnswerHistoryReq(quizId = quiz.quizId!!, answer = "정답"))
        val req = ChallengeQuizReq(questId = "quest-123", answers = submittedAnswers)
        val authReq = AuthReq(userId = "user-1")
        val response: CreateChallengeResp = challengeQuizService.createQuizChallenge(req, authReq)

        // When
        challengeService.delete(response.challengeId!!, authReq)

        // then
        assertThrows(NotFoundException::class.java) {
            challengeService.findById(response.challengeId!!)
        }
    }

    /**
     * 퀘스트를 저장한다.
     */
    private fun saveQuest(): Quest {
        val request = QuestCreateReqForAdmin(
            writer = "admin",
            imageId = "img-123",
            missions = listOf(
                MissionReq(
                    content = "10회 이상 방문",
                    target = "user",
                    quantity = 10,
                    type = MissionType.FREE,
                    quizzes = listOf(
                        QuizReq(
                            question = "몇 회 방문했나요?",
                            hint = "숫자로 입력하세요",
                            answers = listOf(
                                AnswerReq(content = "10")
                            )
                        )
                    )
                )
            ),
            rewards = listOf(
                RewardReq(
                    content = "무료 쿠키 제공",
                    target = "쿠키",
                    quantity = 1,
                    discountRate = 0,
                    type = RewardType.GIFT
                ),
                RewardReq(
                    target = "XP",
                    quantity = 50,
                    type = RewardType.XP,
                    discountRate = null,
                    content = "STRENGTH"
                )
            ),
            score = 100,
            expireDate = "2030-12-31",
            target = QuestTarget.NONE.name,
            type = QuestType.NORMAL.name,
            mainImageId = "main-img-123",
            popularYn = true
        )
        val save = questService.adminSave(request)
        val quest = questService.findModelById(save.questId!!)
        return quest
    }

    @Test
    @DisplayName("XP 적립 후 챌린지 삭제 시 XP 회수")
    fun shouldThrowExceptionWhenXpNotFoundOnChallengeDelete() {
        // Given
        val resp = authResp()
        val quest = saveQuestWithXpReward()
        val mission = missionRepository.findAllByQuestId(quest.questId!!)
        val quiz = quizRepository.save(
            QuizEntity(question = "OX 질문입니다", hint = "힌트", mission = mission.first())
        )
        answerRepository.save(AnswerEntity(content = "O", quiz = quiz))

        val authReq = AuthReq(userId = resp.userId)
        val challengeReq = ChallengeQuizReq(
            questId = quest.questId!!,
            answers = listOf(AnswerHistoryReq(quizId = quiz.quizId!!, answer = "O"))
        )

        val challengeResp = challengeQuizService.createQuizChallenge(challengeReq, authReq)

        // Then
        val challengeId = challengeResp.challengeId!!
        challengeService.delete(challengeId, authReq)
    }

    private fun authResp(): UserResp {
        val email = "user-1@email.com"
        authService.login(
            LoginReq(
                userType = UserType.CUSTOMER,
                accessToken = "",
                refreshToken = "",
                email = email,
                channel = AuthChannel.KAKAO
            )
        )

        return customerService.findByEmail(email)
    }

    private fun saveQuestWithXpReward(): Quest {
        val req = QuestCreateReqForAdmin(
            writer = "admin",
            imageId = "img-123",
            mainImageId = "main-img-123",
            score = 100,
            expireDate = "2030-12-31",
            target = QuestTarget.NONE.name,
            type = QuestType.NORMAL.name,
            popularYn = true,
            missions = listOf(
                MissionReq(
                    content = "OX 퀴즈입니다.",
                    target = null,
                    quantity = null,
                    type = MissionType.OX,
                    quizzes = listOf(
                        QuizReq(
                            question = "OX 질문입니다",
                            hint = "힌트",
                            answers = listOf(
                                AnswerReq(content = "O"),
                                AnswerReq(content = "X")
                            )
                        )
                    )
                )
            ),
            rewards = listOf(
                RewardReq(
                    content = "STRENGTH", // <- XpType enum value
                    target = null,
                    quantity = 50,
                    discountRate = null,
                    type = RewardType.XP
                )
            )
        )

        val saved = questService.adminSave(req)
        return questService.findModelById(saved.questId!!)
    }
}