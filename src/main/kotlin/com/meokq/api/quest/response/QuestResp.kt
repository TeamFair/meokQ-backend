package com.meokq.api.quest.response

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Quest-Response")
class QuestResp(
    @Schema(description = "Unique identifier for the quest")
    val questId: String?,

    @Schema(description = "List of missions in the quest")
    var missions: List<MissionResp> = mutableListOf(),

    @Schema(description = "List of rewards in the quest")
    var rewards: List<RewardResp> = mutableListOf(),
)