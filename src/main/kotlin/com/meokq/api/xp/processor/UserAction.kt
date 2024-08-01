package com.meokq.api.xp.processor

enum class UserAction(
    val title: String,
    val xpPoint: Long,
    ){
    CHALLENGE_REGISTER(title = "챌린지 등록", xpPoint = 50),
    CHALLENGE_DELETE(title = "챌린지 삭제",xpPoint = -50),
    LIKE(title = "좋아요 이모지 등록", xpPoint = 10),
    UNLIKE(title = "좋아요 이모지 삭제", xpPoint = -10),
}