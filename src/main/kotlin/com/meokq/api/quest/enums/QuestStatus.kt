package com.meokq.api.quest.enums

import com.meokq.api.core.exception.InvalidRequestException

enum class QuestStatus(
    val value : String,
    val publishAction: () -> QuestStatus,
    val deleteAction : () -> QuestStatus,
    // 퀘스트 종료 시 호출
) {
    UNDER_REVIEW("검토중",
        publishAction = {PUBLISHED},
        deleteAction = {DELETED}
    ),
    PUBLISHED("게시중",
        publishAction = {throw InvalidRequestException("이미 게시중인 퀘스트입니다.")},
        deleteAction = {DELETED}
    ),
    COMPLETED("종료",
        publishAction = {throw InvalidRequestException("종료된 퀘스트입니다. 검토중인 퀘스트만 게시할 수 있습니다.")},
        deleteAction = {throw InvalidRequestException("종료된 퀘스트입니다.") }
    ),
    DELETED("삭제됨",
        publishAction = {throw InvalidRequestException("삭제된 퀘스트입니다. 검토중인 퀘스트만 게시할 수 있습니다.")},
        deleteAction = {throw InvalidRequestException("삭제된 퀘스트입니다.") }
    ),
}