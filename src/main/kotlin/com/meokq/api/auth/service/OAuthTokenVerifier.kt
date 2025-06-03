package com.meokq.api.auth.service

import com.meokq.api.auth.dto.OAuthTokenClaims
import com.meokq.api.auth.enums.AuthChannel

interface OAuthTokenVerifier {
    val supportedProvider: AuthChannel

    fun verifyToken(idToken: String, clientId: String): OAuthTokenClaims
}
