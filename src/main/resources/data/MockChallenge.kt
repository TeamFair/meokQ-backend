package data

import com.meokq.api.challenge.enums.ChallengeStatus
import com.meokq.api.challenge.model.Challenge
import data.MockCustomer.CS20000003
import data.MockCustomer.CS20000004
import data.MockCustomer.CS20000005
import data.MockImage.IM10000001
import data.MockQuest.QS20000004
import data.MockQuest.QS20000005
import data.MockQuest.QS20000006
import data.MockQuest.QS20000007
import jdk.jfr.Description

object MockChallenge {
    // 승인된 내역
    @Description("승인된 도전내역1")
    val CH20000001 = Challenge(
        challengeId = "CH20000001",
        status = ChallengeStatus.APPROVED,
        rejectReason = null,
        questId = QS20000004.questId,
        customerId = CS20000003.customerId,
        receiptImageId = IM10000001.fileId
    )

    @Description("승인된 도전내역2")
    val CH20000002 = Challenge(
        challengeId = "CH20000002",
        status = ChallengeStatus.APPROVED,
        rejectReason = null,
        questId = QS20000005.questId,
        customerId = CS20000003.customerId,
        receiptImageId = IM10000001.fileId
    )

    @Description("승인된 도전내역2")
    val CH20000003 = Challenge(
        challengeId = "CH20000003",
        status = ChallengeStatus.APPROVED,
        rejectReason = null,
        questId = QS20000006.questId,
        customerId = CS20000003.customerId,
        receiptImageId = IM10000001.fileId
    )

    // 거절된 내역
    @Description("거절된 도전내역1")
    val CH20000004 = Challenge(
        challengeId = "CH20000004",
        status = ChallengeStatus.REJECTED,
        rejectReason = "잘못된 이미지가 포함되어 있습니다.",
        questId = QS20000007.questId,
        customerId = CS20000003.customerId,
        receiptImageId = IM10000001.fileId
    )

    @Description("거절된 도전내역2")
    val CH20000005 = Challenge(
        challengeId = "CH20000005",
        status = ChallengeStatus.REJECTED,
        rejectReason = "잘못된 이미지가 포함되어 있습니다.",
        questId = QS20000004.questId,
        customerId = CS20000004.customerId,
        receiptImageId = IM10000001.fileId
    )

    @Description("거절된 도전내역3")
    val CH20000006 = Challenge(
        challengeId = "CH20000006",
        status = ChallengeStatus.REJECTED,
        rejectReason = "잘못된 이미지가 포함되어 있습니다.",
        questId = QS20000005.questId,
        customerId = CS20000004.customerId,
        receiptImageId = IM10000001.fileId
    )

    // 검토중인 내역
    @Description("검토중된 도전내역1")
    val CH20000007 = Challenge(
        challengeId = "CH20000007",
        status = ChallengeStatus.UNDER_REVIEW,
        rejectReason = null,
        questId = QS20000006.questId,
        customerId = CS20000004.customerId,
        receiptImageId = IM10000001.fileId
    )

    @Description("검토중된 도전내역2")
    val CH20000008 = Challenge(
        challengeId = "CH20000008",
        status = ChallengeStatus.UNDER_REVIEW,
        rejectReason = null,
        questId = QS20000004.questId,
        customerId = CS20000005.customerId,
        receiptImageId = IM10000001.fileId
    )

    @Description("검토중된 도전내역3")
    val CH20000009 = Challenge(
        challengeId = "CH20000009",
        status = ChallengeStatus.UNDER_REVIEW,
        rejectReason = null,
        questId = QS20000006.questId,
        customerId = CS20000005.customerId,
        receiptImageId = IM10000001.fileId
    )


}