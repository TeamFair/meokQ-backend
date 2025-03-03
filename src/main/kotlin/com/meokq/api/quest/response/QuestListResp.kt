package com.meokq.api.quest.response

import com.meokq.api.core.converter.DateTimeConverterV2
import com.meokq.api.quest.enums.MissionType
import com.meokq.api.quest.enums.QuestStatus
import com.meokq.api.quest.model.Quest
import com.querydsl.core.types.dsl.BooleanExpression
import java.time.LocalDateTime

class QuestListResp(
    val questId: String?,
    val marketId: String?,
    var writer: String?= null,
    var status: QuestStatus?,
    var expireDate: String?,
    var creatorRole: String?,
    var imageId: String?= null,
    var type: String?,
    var target: String?,
    var score: Int?,
    var mainImageId: String?,
    var popularYn: Boolean?,
    var createDate: LocalDateTime?,
    var rewardList: MutableList<RewardResp>,
    var missionList: MutableList<MissionResp>,
) {

    constructor(model: Quest) : this(
        questId = model.questId,
        marketId = model.marketId,
        status = model.status,
        expireDate = model.expireDate?.let { DateTimeConverterV2.convertToString(it) },
        creatorRole = model.creatorRole.toString(),
        writer = model.writer,
        imageId = model.imageId,
        type = model.type.name,
        target = model.target.name,
        score = model.score,
        mainImageId = model.mainImageId,
        popularYn = model.popularYn,
        createDate = model.createDate,
        rewardList = model.rewards?.map { RewardResp(it)}?.toMutableList() ?: mutableListOf(),
        missionList = model.missions?.map { MissionResp(it)}?.toMutableList() ?: mutableListOf(),
    )
}
