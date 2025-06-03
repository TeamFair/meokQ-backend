package com.meokq.api.auth.dto

import com.meokq.api.auth.enums.AuthChannel
import java.util.*

interface OAuthTokenClaims {
    val provider: AuthChannel
    val subject: String              // Provider의 고유 사용자 ID
    val email: String?               // 사용자 이메일
    val name: String?                // 사용자 이름
    val emailVerified: Boolean       // 이메일 검증 여부
    val profileImageUrl: String?     // 프로필 이미지
    val issuedAt: Date     // 발급 시간
    val expiresAt: Date    // 만료 시간
}
