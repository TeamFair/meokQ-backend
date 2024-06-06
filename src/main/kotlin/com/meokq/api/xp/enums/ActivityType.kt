package com.meokq.api.xp.enums

enum class ActivityType(val title: String, val xpPoint: Int) {
    CHALLENGE_REGISTER("챌린지 등록", 50),
    LIKE("좋아요", 10),
}