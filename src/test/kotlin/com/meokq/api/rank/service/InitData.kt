package com.meokq.api.rank.service

import com.meokq.api.challenge.enums.ChallengeStatus
import com.meokq.api.challenge.model.Challenge
import com.meokq.api.rank.ChallengeEmojiRankService
import java.time.LocalDateTime
import kotlin.random.Random

class InitData(private val challengeService: ChallengeEmojiRankService) {
    fun createTestChallenges(upperRankCount: Int, lowerRankCount: Int) {
        for (i in 1..upperRankCount) {
            challengeService.addToRank(
                Challenge(
                    challengeId = "challenge-upper-$i",
                    status = ChallengeStatus.values().random(),
                    rejectReason = if (i % 3 == 0) "Reason $i" else null,
                    questId = "quest-$i",
                    customerId = "customer-upper-$i",
                    receiptImageId = "receipt-upper-$i",
                    likeEmojiCnt = Random.nextInt(5, 100),
                    hateEmojiCnt = Random.nextInt(0, 100),
                    viewCount = Random.nextLong(0, 1000),
                    createDate = LocalDateTime.now().minusDays(i.toLong()),
                    updateDate = LocalDateTime.now().minusHours(i.toLong())
                )
            )
        }

        for (i in 1..lowerRankCount) {
            challengeService.addToRank(
                Challenge(
                    challengeId = "challenge-lower-$i",
                    status = ChallengeStatus.values().random(),
                    rejectReason = if (i % 3 == 0) "Reason $i" else null,
                    questId = "quest-$i",
                    customerId = "customer-lower-$i",
                    receiptImageId = "receipt-lower-$i",
                    likeEmojiCnt = Random.nextInt(0, 5),
                    hateEmojiCnt = Random.nextInt(0, 100),
                    viewCount = Random.nextLong(0, 1000),
                    createDate = LocalDateTime.now().minusDays(i.toLong()),
                    updateDate = LocalDateTime.now().minusHours(i.toLong())
                )
            )
        }
    }
}