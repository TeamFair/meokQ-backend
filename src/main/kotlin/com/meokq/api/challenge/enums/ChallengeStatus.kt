package com.meokq.api.challenge.enums

import com.meokq.api.core.exception.InvalidRequestException
enum class ChallengeStatus(
    val value : String,
    val deleteAction : () -> Unit,
) {
    APPROVED(
        value = "승인",
        deleteAction = {throw InvalidRequestException("You can only delete challenges that are under_review.") }
    ),
    REJECTED(
        value = "반려",
        deleteAction = {throw InvalidRequestException("You can only delete challenges that are under_review.") }
    ),
    UNDER_REVIEW(
        value = "검토중",
        deleteAction = {}
    ),
    REPORT(
        value = "신고",
        deleteAction = {throw InvalidRequestException("You can only delete challenges that are under_review.") }
    )
}