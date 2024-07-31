package com.meokq.api.xp.processor

enum class UserAction(
    val title: String,
    val xpPoint: Long,
    ){
    CHALLENGE_REGISTER(title = "챌린지 등록",xpPoint = 50,),
    CHALLENGE_DELETE(title = "챌린지 삭제",xpPoint = -50,),
    LIKE(title = "좋아요", xpPoint = 10),
    UN_LIKE(title = "좋아요 취소", xpPoint = -10),
}