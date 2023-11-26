package com.meokq.api.quest.enums

enum class QuestStatus(val value : String) {
    UNDER_REVIEW("검토중"),   // 검토 중인 상태
    PUBLISHED("게시중"),       // 게시 중인 상태
    COMPLETED("종료")          // 종료된 상태
}