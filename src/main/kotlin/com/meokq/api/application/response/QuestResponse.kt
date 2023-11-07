package com.meokq.api.application.response

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Quest-Response")
class QuestResponse(
    @Schema(description = "Unique identifier for the quest")
    val questId: String?,

    @Schema(description = "List of missions in the quest")
    var missions: List<MissionResponse> = mutableListOf(),

    @Schema(description = "List of rewards in the quest")
    var rewards: List<RewardResponse> = mutableListOf(),
)