package com.meokq.api.auth.service

import com.meokq.api.auth.enums.AuthChannel
import org.springframework.stereotype.Service

@Service
class OAuthProviderFactory(
    private val verifiers: List<OAuthTokenVerifier>
) {

    private val verifierMap: Map<AuthChannel, OAuthTokenVerifier> =
        verifiers.associateBy { it.supportedProvider }

    fun getVerifier(provider: AuthChannel): OAuthTokenVerifier {
        return verifierMap[provider]
            ?: throw IllegalArgumentException("지원하지 않는 OAuth Provider: ${provider.providerName}")
    }
}
