package com.meokq.api.quest.response

import com.meokq.api.quest.model.Quest
import io.swagger.v3.oas.annotations.media.Schema

class QuestUpdateResp(
    @Schema(description = "Unique identifier for the quest")
    val questId: String?,

    @Schema(description = "Unique identifier for the market")
    val marketId: String?,
){
    constructor(model : Quest) : this(
        questId = model.questId,
        marketId = model.marketId,
    )
}