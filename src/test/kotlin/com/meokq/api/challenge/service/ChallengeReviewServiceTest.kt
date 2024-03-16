package com.meokq.api.challenge.service

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional

@Transactional
@SpringBootTest
@ActiveProfiles("local")
internal class ChallengeReviewServiceTest{

    @Autowired
    private lateinit var challengeReviewService: ChallengeReviewService

    @Test
    fun review(){

    }

}