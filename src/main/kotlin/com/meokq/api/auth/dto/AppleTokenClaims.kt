package com.meokq.api.auth.dto

import com.meokq.api.auth.enums.AuthChannel
import java.util.*

data class AppleTokenClaims(
    override val subject: String,
    override val email: String?,
    override val name: String?,
    override val emailVerified: Boolean,
    override val profileImageUrl: String?,
    override val issuedAt: Date,
    override val expiresAt: Date,
) : OAuthTokenClaims {
    override val provider = AuthChannel.APPLE
}
