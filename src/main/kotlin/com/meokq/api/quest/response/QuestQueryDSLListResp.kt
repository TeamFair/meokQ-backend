package com.meokq.api.quest.response

import com.meokq.api.auth.enums.UserType
import com.meokq.api.core.converter.DateTimeConverterV2
import com.meokq.api.quest.enums.MissionType
import com.meokq.api.quest.enums.QuestStatus
import com.meokq.api.quest.model.Quest
import com.querydsl.core.annotations.QueryProjection
import java.time.LocalDateTime

class QuestQueryDSLListResp @QueryProjection constructor(
    val questId: String?,
    val marketId: String?,
    var writer: String? = null,
    var missionTitle: String? = null,
    var rewardList: List<RewardResp>? = mutableListOf(),
    var status: QuestStatus? = null,
    var expireDate: LocalDateTime? = null,
    var creatorRole: UserType? = null,
    var imageId: String? = null
) {

}