package com.meokq.api.challenge.enums

import com.meokq.api.core.exception.InvalidRequestException
enum class ChallengeStatus(
    val value : String,
    // TODO : 240819 챌린지 삭제 정책 변경 (사용자도 삭제 가능하게 변경 됌) 추후 삭제 고려
    val deleteAction : () -> Unit,
) {
    APPROVED(
        value = "승인",
        deleteAction = {}
    ),
    REJECTED(
        value = "반려",
        deleteAction = {}
    ),
    UNDER_REVIEW(
        value = "검토중",
        deleteAction = {}
    ),
    REPORTED(
        value = "신고",
        deleteAction = {}
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