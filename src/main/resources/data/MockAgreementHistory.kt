package data

import com.meokq.api.market.enums.MarketAuthResult
import com.meokq.api.market.model.MarketAuth
import data.MockLicenseAuth.BR20000001
import data.MockLicenseAuth.BR20000002
import data.MockLicenseAuth.BR20000003
import data.MockMarket.MK20000002
import data.MockMarket.MK20000003
import data.MockMarket.MK20000004
import data.MockMarket.MK20000005
import data.MockMarket.MK20000006
import data.MockOperatorAuth.PR20000001
import data.MockOperatorAuth.PR20000002
import data.MockOperatorAuth.PR20000003
import jdk.jfr.Description

object MockAgreementHistory {
    @Description("거절된 마켓인증내역")
    val MA20000001 = MarketAuth(
        recordId = "MA20000001",
        marketId = MK20000003.marketId,
        reviewResult = MarketAuthResult.REJECTED,
        comment = "유효하지 않은 사업자정보입니다.",
        operatorAuth = PR20000001,
        licenseAuth = BR20000001,
    )

    @Description("검토중인 마켓인증내역")
    val MA20000002 = MarketAuth(
        recordId = "MA20000002",
        marketId = MK20000002.marketId,
        reviewResult = MarketAuthResult.REJECTED,
        comment = "Not suitable",
        operatorAuth = PR20000002,
        licenseAuth = BR20000002,
    )

    @Description("승인된 마켓인증내역1")
    val MA20000003 = MarketAuth(
        recordId = "MA20000003",
        marketId = MK20000004.marketId,
        reviewResult = MarketAuthResult.APPROVED,
        comment = null,
        operatorAuth = PR20000003,
        licenseAuth = BR20000003,
    )

    @Description("승인된 마켓인증내역2")
    val MA2000004 = MarketAuth(
        recordId = "MA2000004",
        marketId = MK20000005.marketId,
        reviewResult = MarketAuthResult.APPROVED,
        comment = null,
        operatorAuth = PR20000003,
        licenseAuth = BR20000003,
    )

    @Description("승인된 마켓인증내역3")
    val MA2000005 = MarketAuth(
        recordId = "MA2000005",
        marketId = MK20000006.marketId,
        reviewResult = MarketAuthResult.APPROVED,
        comment = null,
        operatorAuth = PR20000003,
        licenseAuth = BR20000003,
    )
}