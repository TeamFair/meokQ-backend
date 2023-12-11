package com.meokq.api.quest.enums

enum class QuestStatus(
    val value : String,
    val couldDelete : Boolean,
) {
    UNDER_REVIEW("검토중", true),   // 검토 중인 상태
    PUBLISHED("게시중", false),       // 게시 중인 상태
    COMPLETED("종료", false)          // 종료된 상태
}