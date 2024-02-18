package data

import com.meokq.api.quest.enums.QuestStatus
import com.meokq.api.quest.model.Quest
import data.MockMarket.MK20000004
import data.MockMarket.MK20000005
import data.MockMarket.MK20000006
import jdk.jfr.Description

object MockQuest {
    /**
     * 검토중
     */
    @Description("검토중인 Quest1")
    val QS20000001 = Quest(
        questId = "QS20000001",
        status = QuestStatus.UNDER_REVIEW,
        marketId = MK20000004.marketId,
    )

    @Description("검토중인 Quest2")
    val QS20000002 = Quest(
        questId = "QS20000002",
        status = QuestStatus.UNDER_REVIEW,
        marketId = MK20000004.marketId,
    )

    @Description("검토중인 Quest3")
    val QS20000003 = Quest(
        questId = "QS20000003",
        status = QuestStatus.UNDER_REVIEW,
        marketId = MK20000005.marketId,
    )

    /**
     * 게시중
     */
    @Description("게시중인 Quest1")
    val QS20000004 = Quest(
        questId = "QS20000004",
        status = QuestStatus.PUBLISHED,
        marketId = MK20000004.marketId,
    )

    @Description("게시중인 Quest2")
    val QS20000005 = Quest(
        questId = "QS20000005",
        status = QuestStatus.PUBLISHED,
        marketId = MK20000004.marketId,
    )

    @Description("게시중인 Quest3")
    val QS20000006 = Quest(
        questId = "QS20000006",
        status = QuestStatus.PUBLISHED,
        marketId = MK20000005.marketId,
    )

    @Description("게시중인 Quest4")
    val QS20000007 = Quest(
        questId = "QS20000007",
        status = QuestStatus.PUBLISHED,
        marketId = MK20000006.marketId,
    )
    
    /**
     * 종료
     */
    @Description("종료된 Quest1")
    val QS20000008 = Quest(
        questId = "QS20000008",
        status = QuestStatus.COMPLETED,
        marketId = MK20000004.marketId,
    )

    /**
     * 삭제
     */
    @Description("삭제된 Quest1")
    val QS20000009 = Quest(
        questId = "QS20000001",
        status = QuestStatus.DELETED,
        marketId = MK20000004.marketId,
    )
}