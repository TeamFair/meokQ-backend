package com.meokq.api.auth.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.meokq.api.auth.dto.AppleTokenClaims
import com.meokq.api.auth.dto.OAuthTokenClaims
import com.meokq.api.auth.enums.AuthChannel
import com.meokq.api.core.util.RSAPublicKeyUtils
import com.nimbusds.jose.JWSVerifier
import com.nimbusds.jose.crypto.RSASSAVerifier
import com.nimbusds.jwt.SignedJWT
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.security.interfaces.RSAPublicKey
import java.util.*

@Service
class AppleOAuthVerifier(
    private val restTemplate: RestTemplate,
    private val objectMapper: ObjectMapper
) : OAuthTokenVerifier {

    override val supportedProvider = AuthChannel.APPLE

    companion object {
        private const val APPLE_KEYS_URL = "https://appleid.apple.com/auth/keys"
        private const val APPLE_ISSUER = "https://appleid.apple.com"
    }

    override fun verifyToken(idToken: String, clientId: String): OAuthTokenClaims {
        val signedJWT = SignedJWT.parse(idToken)
        val header = signedJWT.header
        val claims = signedJWT.jwtClaimsSet

        // 기본 클레임 검증
        validateBasicClaims(claims, clientId)

        // 서명 검증
        val publicKey = getApplePublicKey(header.keyID)
        val verifier: JWSVerifier = RSASSAVerifier(publicKey)

        if (!signedJWT.verify(verifier)) {
            throw SecurityException("Apple 서명 검증 실패")
        }

        return AppleTokenClaims(
            subject = claims.subject,
            email = claims.getStringClaim("email"),
            name = null,
            emailVerified = claims.getBooleanClaim("email_verified") ?: false,
            profileImageUrl = null,
            issuedAt = claims.issueTime,
            expiresAt = claims.expirationTime,
        )
    }

    private fun validateBasicClaims(claims: com.nimbusds.jwt.JWTClaimsSet, clientId: String) {
        if (claims.issuer != APPLE_ISSUER) {
            throw SecurityException("잘못된 Apple 발급자: ${claims.issuer}")
        }

        if (!claims.audience.contains(clientId)) {
            throw SecurityException("잘못된 Apple 대상: ${claims.audience}")
        }

        if (claims.expirationTime.before(Date())) {
            throw SecurityException("Apple 토큰 만료")
        }
    }

    private fun getApplePublicKey(keyId: String): RSAPublicKey {
        val response = restTemplate.getForObject(APPLE_KEYS_URL, String::class.java)
            ?: throw IllegalStateException("Apple 키 서버 응답 없음")

        val keysData = objectMapper.readTree(response)
        val keyNode = keysData["keys"].find { it["kid"].asText() == keyId }
            ?: throw IllegalArgumentException("Apple Key ID $keyId 를 찾을 수 없음")

        val n = keyNode["n"].asText()
        val e = keyNode["e"].asText()

        return RSAPublicKeyUtils.createFromJwtClaims(n, e)
    }
}
