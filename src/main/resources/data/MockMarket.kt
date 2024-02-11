package data

import com.meokq.api.market.enums.MarketStatus
import com.meokq.api.market.model.Market
import data.MockBoss.BS20000003
import data.MockBoss.BS20000004
import data.MockBoss.BS20000005
import data.MockImage.IM10000003
import jdk.jfr.Description

object MockMarket {
    private const val district1 = "1100000000"
    private const val district2 = "1200000000"

    @Description("등록된 마켓")
    val MK20000001 = Market(
        marketId = "MK20000001",
        name = "Market Name",
        address = "서울특별시 종로구",
        district = district1,
        content = "Market Content",
        phone = "0211111111",
        ticketCount = 10,
        logoImageId = IM10000003.fileId,
        status = MarketStatus.REGISTERED,
        presidentId = BS20000003.bossId
    )

    @Description("검토중인 마켓")
    val MK20000002 = Market(
        marketId = "MK20000002",
        name = "Market Name",
        address = "서울특별시 종로구",
        district = district1,
        content = "Market Content",
        phone = "0222222222",
        ticketCount = 10,
        logoImageId = IM10000003.fileId,
        status = MarketStatus.UNDER_REVIEW,
        presidentId = BS20000004.bossId
    )

    @Description("거부된 마켓")
    val MK20000003 = Market(
        marketId = "MK20000003",
        name = "Market Name",
        address = "서울특별시 종로구",
        district = district2,
        content = "Market Content",
        phone = "0233333333",
        ticketCount = 10,
        logoImageId = IM10000003.fileId,
        status = MarketStatus.REJECTED,
        presidentId = BS20000005.bossId
    )

    @Description("승인된 마켓1")
    val MK20000004 = Market(
        marketId = "MK20000004",
        name = "Market Name",
        address = "서울특별시 종로구",
        district = district1,
        content = "Market Content",
        phone = "0244444444",
        ticketCount = 10,
        logoImageId = IM10000003.fileId,
        status = MarketStatus.UNDER_REVIEW,
        presidentId = "BS10000001"
    )

    @Description("승인된 마켓2")
    val MK20000005 = Market(
        marketId = "MK20000005",
        name = "Market Name",
        address = "서울특별시 종로구",
        district = district2,
        content = "Market Content",
        phone = "0255555555",
        ticketCount = 10,
        logoImageId = IM10000003.fileId,
        status = MarketStatus.UNDER_REVIEW,
        presidentId = "BS10000002"
    )

    @Description("승인된 마켓3")
    val MK20000006 = Market(
        marketId = "MK20000006",
        name = "Market Name",
        address = "서울특별시 종로구",
        district = district2,
        content = "Market Content",
        phone = "0255555555",
        ticketCount = 10,
        logoImageId = IM10000003.fileId,
        status = MarketStatus.REGISTERED,
        presidentId = "BS10000002"
    )

}