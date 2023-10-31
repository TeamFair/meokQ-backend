package com.meokq.api.application.request

class QuestRequest(
    val marketId : String,
    val missions : List<MissionRequest>,
    val rewards : List<RewardRequest>
)