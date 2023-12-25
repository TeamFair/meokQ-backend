package com.meokq.api.quest.response

import com.meokq.api.quest.enums.QuestStatus
import com.meokq.api.quest.model.Mission
import com.meokq.api.quest.model.Quest
import com.meokq.api.quest.model.Reward

class QuestListResp(
    val questId: String?,
    val marketId: String?,
    var missionTitle : String?,
    var rewardTitle : String?,
    var status: QuestStatus?,
) {

    constructor(model: Quest, mission: Mission?, reward: Reward?) : this(
        questId = model.questId,
        marketId = model.marketId,
        missionTitle = mission?.type?.getTitle(mission),
        rewardTitle = reward?.type?.getTitle(reward),
        status = model.status,
    )
}