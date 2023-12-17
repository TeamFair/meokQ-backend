package com.meokq.api.auth.enums

enum class UserType(
    val authorization : String,
    val requestMapper : String,
) {
    BOSS(authorization = "boss", requestMapper = "/api/boss"),
    CUSTOMER(authorization = "customer", requestMapper = "/api/customer"),
    ADMIN(authorization = "admin", requestMapper = "/api/admin"),
    UNKNOWN(authorization = "open", requestMapper = "/api/open");

    companion object {
        fun fromString(value: String): UserType {
            return requireNotNull(values().find { it.name.equals(value, ignoreCase = true) }) {
                "No enum constant UserType for string: $value"
            }
        }
    }
}
