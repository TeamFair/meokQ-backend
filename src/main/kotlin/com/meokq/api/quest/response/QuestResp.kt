package com.meokq.api.quest.response

import com.meokq.api.quest.model.Quest
import io.swagger.v3.oas.annotations.media.Schema

@Deprecated("용도별로 response를 잘게 나누었음.")
@Schema(description = "Quest-Response")
class QuestResp(
    @Schema(description = "Unique identifier for the quest")
    val questId: String?,

    @Schema(description = "Unique identifier for the market")
    val marketId: String?,

    @Schema(description = "List of missions in the quest")
    var missions: List<MissionResp> = mutableListOf(),

    @Schema(description = "List of rewards in the quest")
    var rewards: List<RewardResp> = mutableListOf(),
){
    constructor(model : Quest, missions: List<MissionResp>, rewards: List<RewardResp>) : this(
        questId = model.questId,
        marketId = model.marketId,
        missions = missions,
        rewards =  rewards,
    )

    constructor(model: Quest) : this(
        questId = model.questId,
        marketId = model.marketId,
        missions = model.missions?.map { MissionResp(it) } ?: listOf(),
        rewards =  model.rewards?.map { RewardResp(it) } ?: listOf(),
    )
}