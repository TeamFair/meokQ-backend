package com.meokq.api.quest.response

import com.meokq.api.core.converter.DateTimeConverterV2
import com.meokq.api.quest.enums.MissionType
import com.meokq.api.quest.enums.QuestStatus
import com.meokq.api.quest.model.Quest

class QuestListResp(
    val questId: String?,
    val marketId: String?,
    var writer: String?= null,
    var missionTitle: String?,
    var rewardList: MutableList<RewardResp>,
    var status: QuestStatus?,
    var expireDate: String?,
    var creatorRole: String?,
    var imageId: String?= null,
) {

    constructor(model: Quest) : this(
        questId = model.questId,
        marketId = model.marketId,
        missionTitle = model.missions?.firstOrNull()?.let { MissionType.getTitle(it) },
        rewardList = model.rewards?.let { rewards -> rewards.map { RewardResp(it)}.toMutableList()}?: mutableListOf(),
        status = model.status,
        expireDate = model.expireDate?.let { DateTimeConverterV2.convertToString(it) },
        creatorRole = model.creatorRole.toString(),
        writer = model.writer,
        imageId = model.imageId,
    )
}