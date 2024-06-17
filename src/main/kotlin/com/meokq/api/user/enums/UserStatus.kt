package com.meokq.api.user.enums

import com.meokq.api.core.exception.InvalidRequestException

enum class UserStatus(
    val withdrawnAction: () -> UserStatus,
) {
    // 정상 회원
    ACTIVE(
        withdrawnAction = {DORMANT}
    ),

    // 휴면 회원
    DORMANT(
        withdrawnAction = {throw InvalidRequestException("휴면회원은 탈퇴할수 없습니다.")}
    ),

    // 탈퇴 회원 (회원정보 삭제)
}

/**
 * TODO :  회원 정책 결정 필요.
 *
 * 탈퇴회원의 정보는 언제까지 저장한 후에 삭제할지,
 * 어떤 경우에 휴면회원으로 전환되며,
 * 휴면회원의 정보는 언제까지 저장할 것이고,
 * 어떠한 경우에 휴면회원을 활성화시킬지 등.
 */