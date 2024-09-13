package com.meokq.api.application.repository

import com.meokq.api.challenge.enums.ChallengeStatus
import com.meokq.api.challenge.model.Challenge
import com.meokq.api.challenge.repository.ChallengeRepository
import com.meokq.api.challenge.request.ChallengeSearchDto
import com.meokq.api.challenge.specification.ChallengeSpecifications
import com.meokq.api.quest.model.Quest
import com.meokq.api.quest.repository.QuestRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.jpa.domain.Specification
import org.springframework.test.context.ActiveProfiles
import java.time.LocalDateTime.now
import java.util.*

@DataJpaTest
@ActiveProfiles("local")
class ChallengeRepositoryTest {

    @Autowired
    lateinit var repository : ChallengeRepository
    @Autowired
    lateinit var questRepository: QuestRepository

    @Test
    fun testSave(){
        // Create a test challenge
        val challenge = Challenge()
        challenge.status = ChallengeStatus.UNDER_REVIEW
        challenge.createDate = now()

        // Save the challenge to the database
        repository.save(challenge)
        val result = repository.findAll().firstOrNull { it.createDate == challenge.createDate }

        // Then
        Assertions.assertNotNull(result)
        Assertions.assertNotNull(result!!.challengeId)
        Assertions.assertSame(challenge.createDate, result.createDate)
        Assertions.assertSame(challenge.status, result.status)

    }

    @Test
    fun testFindBySpecification() {
        // Given
        val testQuest01 = questRepository.save(Quest(marketId = "MK"+UUID.randomUUID()))
        val testQuest02 = questRepository.save(Quest(marketId = "MK"+UUID.randomUUID()))
        val testCustomerId01 = "CS"+UUID.randomUUID()
        val testCustomer02 = "CS"+UUID.randomUUID()

        repository.save(Challenge(customerId = testCustomerId01, questId = testQuest01.questId))
        repository.save(Challenge(customerId = testCustomerId01, questId = testQuest01.questId))
        repository.save(Challenge(customerId = testCustomerId01, questId = testQuest02.questId))
        repository.save(Challenge(customerId = testCustomer02, questId = testQuest02.questId))

        val challengeSearchDto = ChallengeSearchDto(
            userId = testCustomerId01
        )

        // when
        val specification: Specification<Challenge> = ChallengeSpecifications.joinAndFetch(
            searchDto = challengeSearchDto
        )
        val result = repository.findAll(specification)

        // Then
        Assertions.assertEquals(3, result.size)
    }
}