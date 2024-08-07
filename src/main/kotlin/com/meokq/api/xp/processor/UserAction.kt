package com.meokq.api.xp.processor

enum class UserAction(
    val title: String,
    var xpPoint: Long = 0,
    ){
    CHALLENGE_REGISTER(title = "챌린지 등록"),
    CHALLENGE_DELETE(title = "챌린지 삭제"),
    LIKE(title = "좋아요 이모지 등록", xpPoint = 10),
    HATE(title = "싫어요 이모지 등록", xpPoint = 0)
    ;

    fun xpCustomer(xpPoint: Long): UserAction {
            return this.also {
                it.xpPoint = xpPoint
            }
    }

}