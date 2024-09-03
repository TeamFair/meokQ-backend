package com.meokq.api.xp.model

enum class XpType {
    STRENGTH,
    INTELLECT,
    FUN,
    CHARM,
    SOCIABILITY;
    companion object {
        fun validate(req: String): Boolean {
            return values().any { it.name.equals(req, ignoreCase = true) }
        }
    }
}