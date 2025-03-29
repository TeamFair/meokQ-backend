package com.meokq.api.quest.response

import com.meokq.api.challenge.model.Challenge
import com.meokq.api.challenge.response.ChallengeResp
import com.meokq.api.quest.enums.MissionType
import com.meokq.api.quest.enums.QuestStatus
import com.meokq.api.quest.model.Quest
import java.io.Serializable
import java.time.LocalDateTime

data class QuestCustomerResp(
    val questId: String?,
    val marketId: String?,
    var missionTitles: List<String>?,
    var rewardList: List<RewardResp>?,
    val status: QuestStatus?,
    val expiredData : LocalDateTime,
    var imageId: String?,
    var score :Int = 0,
    var type: String?,
    var target: String?,
    var topLikeChallenges: List<ChallengeResp>?,
    var customerRank: Int?,
    var favoriteYn: Boolean = false,
): Serializable {

    constructor(quest: Quest, topLikeChallenges: List<Challenge>, customerRank: Int?, favoriteYn: Boolean) : this(
        questId = quest.questId,
        marketId = quest.marketId,
        missionTitles = quest.missions?.map { MissionType.getTitle(it) },
        rewardList = quest.rewards?.map { RewardResp(it) },
        status = quest.status,
        expiredData = quest.expireDate ?: LocalDateTime.of(9999, 12, 31, 0, 0, 0),
        imageId = quest.imageId,
        score = quest.score,
        type = quest.type.name,
        target = quest.target.name,
        topLikeChallenges = topLikeChallenges.map { ChallengeResp(it, null) },
        customerRank = customerRank,
        favoriteYn = favoriteYn,
    )
}
