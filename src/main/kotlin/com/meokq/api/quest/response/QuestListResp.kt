package com.meokq.api.quest.response

import com.meokq.api.core.converter.DateTimeConverterV2
import com.meokq.api.quest.enums.MissionType
import com.meokq.api.quest.enums.QuestStatus
import com.meokq.api.quest.enums.RewardType
import com.meokq.api.quest.model.Quest

class QuestListResp(
    val questId: String?,
    val marketId: String?,
    var writer: String?= null,
    val quantity: Int?,
    var missionTitle : String?,
    var rewardTitle : String?,
    var status: QuestStatus?,
    var expireDate: String?,
    var creatorRole : String?,
    var imageId: String?= null,
) {

    constructor(model: Quest) : this(
        questId = model.questId,
        marketId = model.marketId,
        quantity = model.rewards?.firstOrNull()?.quantity,
        missionTitle = model.missions?.firstOrNull()?.let { MissionType.getTitle(it) },
        rewardTitle = model.rewards?.firstOrNull()?.let { RewardType.getTitle(it) },
        status = model.status,
        expireDate = model.expireDate?.let { DateTimeConverterV2.convertToString(it) },
        creatorRole = model.creatorRole.toString(),
        writer = model.writer,
        imageId = model.imageId,
    )
}