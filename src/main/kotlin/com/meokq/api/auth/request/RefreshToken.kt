package com.meokq.api.auth.request

import java.time.Duration

class RefreshToken(
    private val profile: String,
    var userId: String? = "",
    val data: String,
    val ttl: Duration,
){
    val key: String = "$profile-$userId"

    companion object {
        fun getKey(profile: String, userId: String,): String {
            return "$profile-$userId"
        }
    }
}
