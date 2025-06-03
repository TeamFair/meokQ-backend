package com.meokq.api.auth.enums

enum class AuthChannel(
    val providerName: String,
) {
    KAKAO("kakao"),
    GOOGLE("google"),
    APPLE("apple")
    ;
}
