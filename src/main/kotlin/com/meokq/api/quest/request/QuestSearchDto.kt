package com.meokq.api.quest.request

import com.meokq.api.quest.enums.QuestStatus
import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "Quest-Search-Req")
data class QuestSearchDto(
    @Schema(description = "퀘스트를 등록한 마켓 ID")
    val marketId : String? = null,

    @Schema(description = "quest 식별자")
    val questId : String? = null,

    @Schema(description = "퀘스트 상태")
    val status: QuestStatus? = null
)