package com.meokq.api.quest.response

import com.meokq.api.quest.enums.QuestStatus
import com.meokq.api.quest.model.Quest

class QuestDetailResp(
    val questId: String?,
    val marketId: String?,
    var missions: List<MissionResp>?,
    var rewards: List<RewardResp>?,
    val status : QuestStatus?,
){
    constructor(model : Quest) : this(
        questId = model.questId,
        marketId = model.marketId,
        missions = model.missions?.map { MissionResp(it) },
        rewards =  model.rewards?.map { RewardResp(it) },
        status = model.status,
    )
}