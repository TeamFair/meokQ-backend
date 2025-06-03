package com.meokq.api.auth.dto

import com.meokq.api.auth.enums.AuthChannel
import com.meokq.api.auth.enums.OsType
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class OAuthProperties {
    @Value("\${oauth.aos.google.client-id}")
    lateinit var aosGoogleClientId: String
    @Value("\${oauth.ios.google.client-id}")
    lateinit var iosGoogleClientId: String
    @Value("\${oauth.ios.apple.client-id}")
    lateinit var iosAppleClientId: String

    fun getClientIdForProvider(provider: AuthChannel, osType: OsType): String {
        if (provider == AuthChannel.GOOGLE && osType == OsType.AOS) {
            return aosGoogleClientId
        } else if (provider == AuthChannel.GOOGLE && osType == OsType.IOS) {
            return iosGoogleClientId
        } else if (provider == AuthChannel.APPLE && osType == OsType.IOS) {
            return iosAppleClientId
        } else {
            throw IllegalArgumentException("Unknown provider $provider")
        }
    }
}
