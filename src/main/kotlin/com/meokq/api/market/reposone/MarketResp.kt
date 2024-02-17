package com.meokq.api.market.reposone

import com.meokq.api.file.response.ImageResp
import com.meokq.api.market.enums.MarketStatus
import com.meokq.api.market.model.Market
import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "Market-Response")
class MarketResp(
    @Schema(description = "마켓 아이디")
    val marketId: String?,

    // TODO : 추후 제거
    @Schema(description = "logo 이미지")
    var logoImage: ImageResp?,

    var logoImageId: String?,

    @Schema(description = "가게명(점포명)")
    val name: String?,

    @Schema(description = "법정동 코드")
    val district: String?,

    @Schema(description = "주소")
    val address: String?,

    @Schema(description = "점포 상태(검토중, 승인, 반려)")
    var status: MarketStatus?,

    @Schema(description = "게시중인 퀘스트 개수")
    val questCount: Long?,

    @Schema(description = "영업시간(당일)")
    var marketTime: MarketTimeResp?
) {
    constructor(
        model: Market,
        marketTime: MarketTimeResp?,
        questCount: Long,
    ) : this(
        marketId = model.marketId,
        logoImage = null,
        logoImageId = model.logoImageId,
        name = model.name,
        district = model.district,
        address = model.address,
        status = model.status,
        questCount = questCount,
        marketTime = marketTime
    )

    constructor() : this(
        marketId = null,
        logoImage = null,
        logoImageId = null,
        name = null,
        district = null,
        address = null,
        status = null,
        questCount = null,
        marketTime = null
    )
}