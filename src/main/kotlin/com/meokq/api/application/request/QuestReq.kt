package com.meokq.api.application.request

import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "Quest-Request")
class QuestReq(
    @Schema(description = "Unique identifier for the market")
    val marketId : String, // TODO : 추후 제거
    @Schema(description = "List of missions in the quest")
    val missions : List<MissionReq>,
    @Schema(description = "List of rewards in the quest")
    val rewards : List<RewardReq>
)