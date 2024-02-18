package data

import com.meokq.api.quest.enums.MissionType
import com.meokq.api.quest.model.Mission
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

object MockMission {
    // TEA 미션들
    @Description("TEA 5잔 주문")
    val MS20000001 = Mission(
        missionId = "MS20000001",
        questId = QS20000001.questId,
        quantity = 5,
        content = null,
        target = "TEA",
        type = MissionType.NORMAL,
    )

    @Description("TEA 5잔 주문")
    val MS20000002 = Mission(
        missionId = "MS20000002",
        questId = QS20000002.questId,
        quantity = 5,
        content = null,
        target = "TEA",
        type = MissionType.NORMAL,
    )

    @Description("TEA 5잔 주문")
    val MS20000003 = Mission(
        missionId = "MS20000003",
        questId = QS20000003.questId,
        quantity = 5,
        content = null,
        target = "TEA",
        type = MissionType.NORMAL,
    )

    // 쿠키 미션들
    @Description("쿠키 3개 주문")
    val MS20000004 = Mission(
        missionId = "MS20000004",
        questId = QS20000004.questId,
        quantity = 3,
        content = null,
        target = "쿠키",
        type = MissionType.NORMAL,
    )

    @Description("쿠키 3개 주문")
    val MS20000005 = Mission(
        missionId = "MS20000005",
        questId = QS20000005.questId,
        quantity = 3,
        content = null,
        target = "쿠키",
        type = MissionType.NORMAL,
    )

    @Description("쿠키 3개 주문")
    val MS20000006 = Mission(
        missionId = "MS20000006",
        questId = QS20000006.questId,
        quantity = 3,
        content = null,
        target = "쿠키",
        type = MissionType.NORMAL,
    )

    // SNS 홍보 미션들
    @Description("SNS 홍보글 작성")
    val MS20000007 = Mission(
        missionId = "MS20000007",
        questId = QS20000007.questId,
        quantity = null,
        content = "FREE/SNS 홍보글 작성",
        target = null,
        type = MissionType.FREE,
    )

    @Description("SNS 홍보글 작성")
    val MS20000008 = Mission(
        missionId = "MS20000008",
        questId = QS20000008.questId,
        quantity = null,
        content = "FREE/SNS 홍보글 작성",
        target = null,
        type = MissionType.FREE,
    )

    @Description("SNS 홍보글 작성")
    val MS20000009 = Mission(
        missionId = "MS20000009",
        questId = QS20000009.questId,
        quantity = null,
        content = "FREE/SNS 홍보글 작성",
        target = null,
        type = MissionType.FREE,
    )
    
}