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
    REPORTED(
        value = "신고",
        deleteAction = {throw InvalidRequestException("You can only delete challenges that are under_review.") }
    );

    companion object{
        fun fromString(value: String): ChallengeStatus {
            return when (value.uppercase()) {
                APPROVED.name -> APPROVED
                REJECTED.name -> REJECTED
                UNDER_REVIEW.name -> UNDER_REVIEW
                REPORTED.name -> REPORTED
                else -> {
                    throw InvalidRequestException("존재하지 않는 ChallengeStatus 입니다.")
                }
            }
        }
    }

}