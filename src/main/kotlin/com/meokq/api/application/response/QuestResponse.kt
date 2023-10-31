package com.meokq.api.application.response

class QuestResponse(
    val questId : String?,
    var missions : List<MissionResponse> = mutableListOf(),
    var rewards : List<RewardResponse> = mutableListOf()
)