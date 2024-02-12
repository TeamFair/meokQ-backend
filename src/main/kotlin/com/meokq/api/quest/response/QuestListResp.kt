package com.meokq.api.quest.response

import com.meokq.api.quest.enums.MissionType
import com.meokq.api.quest.enums.QuestStatus
import com.meokq.api.quest.enums.RewardType
import com.meokq.api.quest.model.Quest

class QuestListResp(
    val questId: String?,
    val marketId: String?,
    var missionTitle : String?,
    var rewardTitle : String?,
    var status: QuestStatus?,
) {

    constructor(model: Quest) : this(
        questId = model.questId,
        marketId = model.marketId,
        missionTitle = model.missions?.first()?.let { MissionType.getTitle(it) },
        rewardTitle = model.rewards?.first()?.let { RewardType.getTitle(it) },
        status = model.status,
    )
}