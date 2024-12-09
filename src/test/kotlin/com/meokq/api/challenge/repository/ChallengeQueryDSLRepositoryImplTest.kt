package com.meokq.api.challenge.repository

import com.meokq.api.auth.enums.AuthChannel
import com.meokq.api.challenge.enums.ChallengeStatus
import com.meokq.api.challenge.model.Challenge
import com.meokq.api.challenge.request.ChallengeSearchDto
import com.meokq.api.market.model.Market
import com.meokq.api.quest.enums.QuestStatus
import com.meokq.api.quest.enums.QuestTarget
import com.meokq.api.quest.enums.QuestType
import com.meokq.api.quest.model.Quest
import com.meokq.api.user.model.Boss
import com.meokq.api.user.model.Customer
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Profile
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.UUID

@Transactional
@SpringBootTest
@ActiveProfiles("local")
internal class ChallengeQueryDSLRepositoryImplTest{

    @Autowired
    private lateinit var repository: ChallengeQueryDSLRepositoryImpl
    @Autowired
    private lateinit var em: EntityManager

    // test data
    lateinit var boss:Boss
    lateinit var market: Market
    lateinit var quest: Quest
    lateinit var customer: Customer

    @BeforeEach
    fun setup() {
        boss = Boss(email = "userEmail${UUID.randomUUID()}", channel = AuthChannel.APPLE)
        em.persist(boss)
        market = Market(presidentId = boss.bossId, name = "market-name")
        em.persist(market)

        quest = Quest(
            status = QuestStatus.PUBLISHED,
            marketId = market.marketId,
            expireDate = LocalDateTime.now().plusDays(10),
            type = QuestType.NORMAL,
            target = QuestTarget.NONE
        )
        em.persist(quest)

        customer = Customer(email = "userEmail${UUID.randomUUID()}", channel = AuthChannel.APPLE)
        em.persist(customer)

        em.persist(Challenge(status = ChallengeStatus.APPROVED, customerId = customer.customerId, questId = quest.questId))
        em.persist(Challenge(status = ChallengeStatus.APPROVED, customerId = customer.customerId))

        em.flush()
        em.clear()
    }

    @Test
    @DisplayName("정렬 조건에 따라 반환된 도전 목록이 올바르게 정렬된다")
    fun `findAll should return sorted challenges by createDate`() {
        // Given
        val searchDto = ChallengeSearchDto(
            questId = null,
            userId = customer.customerId,
            status = null
        )
        val pageable = PageRequest.of(0, 10, Sort.by("createDate").ascending())

        // When
        val result = repository.findAll(searchDto, pageable)

        // Then
        assertThat(result.content).hasSize(2)
        assertThat(result.content[0].createdAt).isAfterOrEqualTo(result.content[1].createdAt)
    }
}