package com.meokq.api.logs.processor

enum class UserAction(
    val title: String,
    val xpPoint: Long,
    ) {
    CHALLENGE_REGISTER(title = "챌린지 등록",xpPoint = 50,),
    LIKE(title = "좋아요", xpPoint = 10),
}