package com.meokq.api.challenge.service

import com.meokq.api.challenge.enums.ChallengeReviewResult
import com.meokq.api.challenge.request.ChallengeReviewReq
import com.meokq.api.core.exception.InvalidRequestException
import com.meokq.api.core.exception.NotFoundException
import com.meokq.api.coupon.service.CouponService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
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

    @Autowired
    private lateinit var challengeService: ChallengeService

    @Autowired
    private lateinit var couponService: CouponService

    @Test
    @DisplayName("도전내역을 정상적으로 승인합니다.")
    fun `review-approve`(){
        // given
        val request = ChallengeReviewReq(
            challengeId = "CH10000001",
            result = ChallengeReviewResult.APPROVED,
            comment = null,
            marketId = "MK00000001"
        )

        // when
        challengeReviewService.review(request)

        // then
        val challenge = challengeService.findModelById(request.challengeId)
        Assertions.assertEquals(request.challengeId, challenge.challengeId)
        Assertions.assertEquals(request.result.status, challenge.status)
    }

    @Test
    @DisplayName("도전내역을 정상적으로 승인합니다.: 쿠폰이 정상적으로 발급되었습니다.")
    fun `review-approve-coupon`(){
        // given
        val request = ChallengeReviewReq(
            challengeId = "CH10000001",
            result = ChallengeReviewResult.APPROVED,
            comment = null,
            marketId = "MK00000001"
        )

        // when
        challengeReviewService.review(request)

        // then
        val couponList = couponService.findAllByChallengeId(request.challengeId)
        val couponListForChallenge = couponList.stream().filter{it.challengeId==request.challengeId}.toList()
        Assertions.assertTrue(couponListForChallenge.isNotEmpty())
    }

    @Test
    @DisplayName("도전내역을 정상적으로 거부합니다.")
    fun `review-reject`(){
        // given
        val request = ChallengeReviewReq(
            challengeId = "CH10000001",
            result = ChallengeReviewResult.REJECTED,
            comment = "테스트입니다.",
            marketId = "MK00000001"
        )

        // when
        challengeReviewService.review(request)

        // when
        val challenge = challengeService.findModelById(request.challengeId)
        Assertions.assertEquals(request.challengeId, challenge.challengeId)
        Assertions.assertEquals(request.result.status, challenge.status)
    }

    @Test
    @DisplayName("도전내역-검토결과등록에 실패하였습니다.: 마켓에서 등록한 퀘스트가 아닐때")
    fun `review-approve-invaild-market`(){
        // given
        val request = ChallengeReviewReq(
            challengeId = "CH10000001",
            result = ChallengeReviewResult.APPROVED,
            comment = null,
            marketId = "MK00000002"
        )

        // when
        Assertions.assertThrows(InvalidRequestException::class.java){
            challengeReviewService.review(request)
        }
    }

    @Test
    @DisplayName("도전내역-검토결과등록에 실패하였습니다.: 존재하지 않는 도전내역")
    fun `review-invalid-challenge`(){
        // given
        val request = ChallengeReviewReq(
            challengeId = "CH00000000",
            result = ChallengeReviewResult.APPROVED,
            comment = null,
            marketId = "MK00000002"
        )

        // when
        Assertions.assertThrows(NotFoundException::class.java){
            challengeReviewService.review(request)
        }
    }
}