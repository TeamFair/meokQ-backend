package com.meokq.api.rank

import jakarta.transaction.Transactional
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@Transactional
@SpringBootTest
@ActiveProfiles("local")
class ChallengeEmojiRankServiceTest {

    @Autowired
    private lateinit var challengeService: ChallengeEmojiRankService

    private lateinit var initData: InitData

    @BeforeEach
    fun setUp() {
        initData = InitData(challengeService)
           // 테스트 데이터 추가
    }
    @Test
    fun `상위랭크와 하위랭크의 데이터가 충분 할때 10개의 데이터가 나와야 한다`() {
        // Mockito 설정
        val pageNumber = 0
        val pageSize = 10
        challengeService.clearRank()
        initData.createTestChallenges(21, 10)

        val result = challengeService.fetchShuffleRankToPage(pageNumber, pageSize)

        assertEquals(pageSize, result.size)
    }
    @Test
    fun `상위랭크의 데이터가 충분 하지 않을때 하위랭크의 데이터가 채워져 10개의 데이터가 나와야 한다`() {
        // Mockito 설정
        val pageNumber = 1
        val pageSize = 10
        challengeService.clearRank()
        initData.createTestChallenges(1, 10)

        val result = challengeService.fetchShuffleRankToPage(pageNumber, pageSize)

        assertEquals(1, result.size)
    }
    @Test
    fun `하위랭크의 데이터가 충분 하지 않을때 상위랭크의 데이터가 채워져 10개의 데이터가 나와야 한다`() {
        // Mockito 설정
        val pageNumber = 3
        val pageSize = 10
        challengeService.clearRank()
        initData.createTestChallenges(21, 10)

        val result = challengeService.fetchShuffleRankToPage(pageNumber, pageSize)

        assertEquals(1, result.size)
    }
}