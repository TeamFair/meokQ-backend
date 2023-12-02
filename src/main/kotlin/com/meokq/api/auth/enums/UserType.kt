package com.meokq.api.auth.enums

enum class UserType {
    BOSS, CUSTOMER, ADMIN;

    companion object {
        fun fromString(value: String): UserType {
            return requireNotNull(values().find { it.name.equals(value, ignoreCase = true) }) {
                "No enum constant UserType for string: $value"
            }
        }
    }
}
