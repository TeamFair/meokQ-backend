package com.meokq.api.xp

enum class ActivityType(val title: String, val xpPoint: Int) {
    CHALLENGE_REGISTER("챌린지 등록", 50),
    LIKE("좋아요", 10),
}