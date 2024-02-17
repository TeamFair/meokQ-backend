package com.meokq.api.quest.response

import com.meokq.api.quest.enums.MissionType
import com.meokq.api.quest.enums.QuestStatus
import com.meokq.api.quest.enums.RewardType
import com.meokq.api.quest.model.Quest

class QuestDetailResp(
    val questId: String?,
    val marketId: String?,

    // 2024-02-18 IQU004 응답 형태 단순화
    var missionTitles: List<String>?,
    var rewardTitles: List<String>?,
    //var missions: List<MissionResp>?,
    //var rewards: List<RewardResp>?,
    val status: QuestStatus?,
){
    constructor(model : Quest) : this(
        questId = model.questId,
        marketId = model.marketId,
        missionTitles = model.missions?.map { MissionType.getTitle(it) },
        rewardTitles = model.rewards?.map { RewardType.getTitle(it) },
        //missions = model.missions?.map { MissionResp(it) },
        //rewards =  model.rewards?.map { RewardResp(it) },
        status = model.status,
    )
}