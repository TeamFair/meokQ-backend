package com.meokq.api.application.repository

import com.meokq.api.challenge.enums.ChallengeStatus
import com.meokq.api.challenge.model.Challenge
import com.meokq.api.challenge.repository.ChallengeRepository
import com.meokq.api.challenge.request.ChallengeSearchDto
import com.meokq.api.challenge.specification.ChallengeSpecifications
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.jpa.domain.Specification
import org.springframework.test.context.ActiveProfiles
import java.time.LocalDateTime.now

@DataJpaTest
@ActiveProfiles("local")
class ChallengeRepositoryTest {

    @Autowired
    lateinit var repository : ChallengeRepository

    @Test
    fun testFindAllByQuestMarketId() {
        // Save the challenge to the database

        // Use the repository method to find challenges by marketId
        //val challenges = repository.findAllByMarketIdAndStatus("YourMarketId", ChallengeStatus.UNDER_REVIEW,Pageable.unpaged())

        // Assert and perform your test logic here
        // ...
    }

    @Test
    fun testSave(): Challenge? {
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

        return result
    }

    @Test
    fun updateRejectReasonAndStatus(){


        // select가 update보다 먼저 일어남
        /*val result2 = repository.findById(result?.challengeId!!)
        println(result)
        Assertions.assertSame(ChallengeStatus.REJECTED, result2.get().status)
        Assertions.assertSame("reject-test", result2.get().rejectReason)*/
    }

    @Test
    fun testFindBySpecification() {
        // Given
        val challengeSearchDto = ChallengeSearchDto(
            marketId = "MK00000003",
            userId = "CS10000001"
        )

        // when
        val specification: Specification<Challenge> = ChallengeSpecifications.joinAndFetch(
            searchDto = challengeSearchDto
        )
        val result = repository.findAll(specification)

        // Then
        assert(!result.isEmpty()) { "Result should not be empty" }
    }
}