package com.meokq.api.challenge.enums

enum class ChallengeReviewResult(val status: ChallengeStatus) {
    APPROVED(ChallengeStatus.APPROVED),
    REJECTED(ChallengeStatus.REJECTED),
}