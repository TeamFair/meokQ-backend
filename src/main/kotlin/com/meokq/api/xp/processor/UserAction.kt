package com.meokq.api.xp.processor

import com.meokq.api.xp.model.XpType

enum class UserAction(val title: String, var xpPoint: Long = 0) {

    CHALLENGE_REGISTER(title = "챌린지 등록"),
    CHALLENGE_DELETE(title = "챌린지 삭제"),
    CHALLENGE_REPORTED(title = "챌린지 신고로 인한 경험치 회수"),
    LIKE(title = "좋아요 이모지 등록", xpPoint = 10),
    LIKE_CANCEL(title = "좋아요 이모지 취소", xpPoint = -10),
    HATE(title = "싫어요 이모지 등록", xpPoint = 0),
    HATE_CANCEL(title = "싫어요 이모지 취소", xpPoint = 0);

    lateinit var xpType: XpType

    fun xpCustomer(xpType: XpType, xpPoint: Long) : UserAction {
        return this.xpCustomer(xpType = xpType, xpPoint= xpPoint)
    }
}
