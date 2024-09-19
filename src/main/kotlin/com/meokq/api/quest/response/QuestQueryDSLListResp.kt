package com.meokq.api.quest.response

import com.meokq.api.auth.enums.UserType
import com.meokq.api.quest.enums.QuestStatus
import com.meokq.api.quest.model.Mission
import com.meokq.api.quest.model.Quest
import com.querydsl.core.annotations.QueryProjection
import java.time.LocalDateTime

class QuestQueryDSLListResp @QueryProjection constructor(
    quest: Quest, mission: Mission?
) {
    val questId: String? = quest.questId
    val marketId: String? = quest.marketId
    var writer: String? = quest.writer
    var missionTitle: String? = mission.let { mission?.content }?: "미션이 존재하지 않습니다."
    var status: QuestStatus? = quest.status
    var expireDate: LocalDateTime? = quest.expireDate
    var creatorRole: UserType? = quest.creatorRole
    var imageId: String? = quest.imageId
    var score = quest.score

    lateinit var rewardList: List<RewardResp>

    fun addRewardList(rewardList: List<RewardResp>) {
        this.rewardList = rewardList
    }

}