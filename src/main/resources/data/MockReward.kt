package data

import com.meokq.api.quest.enums.RewardType
import com.meokq.api.quest.model.Reward
import data.MockQuest.QS20000001
import data.MockQuest.QS20000002
import data.MockQuest.QS20000003
import data.MockQuest.QS20000004
import data.MockQuest.QS20000005
import data.MockQuest.QS20000006
import data.MockQuest.QS20000007
import data.MockQuest.QS20000008
import data.MockQuest.QS20000009
import jdk.jfr.Description

object MockReward {
    @Description("coffee 90% 할인")
    val RW20000001: Reward = Reward(
        rewardId = "RW20000001",
        questId = QS20000001.questId,
        discountRate = 90,
        content = null,
        target = "COFFEE",
        type = RewardType.DISCOUNT
    )

    @Description("coffee 90% 할인")
    val RW20000002: Reward = Reward(
        rewardId = "RW20000002",
        questId = QS20000002.questId,
        discountRate = 90,
        content = null,
        target = "COFFEE",
        type = RewardType.DISCOUNT
    )

    @Description("coffee 90% 할인")
    val RW20000003: Reward = Reward(
        rewardId = "RW20000003",
        questId = QS20000003.questId,
        discountRate = 90,
        content = null,
        target = "COFFEE",
        type = RewardType.DISCOUNT
    )

    @Description("donut 1개 증정")
    val RW20000004: Reward = Reward(
        rewardId = "RW20000004",
        questId = QS20000004.questId,
        quantity = 1,
        target = "DONUT",
        type = RewardType.GIFT
    )

    @Description("donut 1개 증정")
    val RW20000005: Reward = Reward(
        rewardId = "RW20000005",
        questId = QS20000005.questId,
        quantity = 1,
        target = "DONUT",
        type = RewardType.GIFT
    )

    @Description("donut 1개 증정")
    val RW20000006: Reward = Reward(
        rewardId = "RW20000006",
        questId = QS20000006.questId,
        quantity = 1,
        target = "DONUT",
        type = RewardType.GIFT
    )

    @Description("cookie 1개 증정")
    val RW20000007: Reward = Reward(
        rewardId = "RW20000007",
        questId = QS20000007.questId,
        quantity = 1,
        target = "COOKIE",
        type = RewardType.GIFT
    )

    @Description("cookie 1개 증정")
    val RW20000008: Reward = Reward(
        rewardId = "RW20000008",
        questId = QS20000008.questId,
        quantity = 1,
        target = "COOKIE",
        type = RewardType.GIFT
    )

    @Description("cookie 1개 증정")
    val RW20000009: Reward = Reward(
        rewardId = "RW20000009",
        questId = QS20000009.questId,
        quantity = 1,
        target = "COOKIE",
        type = RewardType.GIFT
    )

}
