package com.meokq.api.market.reposone

import com.meokq.api.market.enums.MarketStatus
import com.meokq.api.market.model.Market
import io.swagger.v3.oas.annotations.media.Schema

class MarketDetailResp(
    @Schema(description = "마켓 아이디")
    val marketId : String?,

    @Schema(description = "가게명(점포명)")
    val name : String?,

    @Schema(description = "가게(점포) 연락처")
    val phone : String?,

    @Schema(description = "법정동 코드")
    val district : String?,

    @Schema(description = "주소")
    val address : String?,

    @Schema(description = "점포 상태(검토중, 승인, 반려)")
    var status : MarketStatus?,

    @Schema(description = "사용가능한 미션 개수")
    val ticketCount : Long?,

    @Schema(description = "영업시간")
    var marketTimes : List<MarketTimeResp>
) {
    constructor(
        model : Market,
        marketTimes: List<MarketTimeResp>,
    ) : this(
        marketId = model.marketId,
        name = model.name,
        phone = model.phone,
        district = model.district,
        address = model.address,
        status = model.status,
        ticketCount = model.ticketCount,
        marketTimes = marketTimes
    )
}