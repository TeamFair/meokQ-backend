package com.meokq.api.auth.service

import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.meokq.api.auth.dto.GoogleTokenClaims
import com.meokq.api.auth.dto.OAuthTokenClaims
import com.meokq.api.auth.enums.AuthChannel
import org.springframework.stereotype.Service
import java.util.*


@Service
class GoogleOAuthVerifier : OAuthTokenVerifier {

    override val supportedProvider = AuthChannel.GOOGLE

    override fun verifyToken(idToken: String, clientId: String): OAuthTokenClaims {
        val verifier = GoogleIdTokenVerifier.Builder(NetHttpTransport(), JacksonFactory.getDefaultInstance())
            .setAudience(Collections.singletonList(clientId))
            .build()

        val googleIdToken = verifier.verify(idToken) ?: throw SecurityException("Google 서명 검증 실패")
        val claims = googleIdToken.payload

        return GoogleTokenClaims(
            subject = claims.subject,
            email = claims.email,
            name = claims["name"]?.toString(),
            emailVerified = claims.emailVerified ?: false,
            profileImageUrl = claims["picture"]?.toString(),
            issuedAt = Date(claims.issuedAtTimeSeconds),
            expiresAt = Date(claims.expirationTimeSeconds),
        )
    }
}
