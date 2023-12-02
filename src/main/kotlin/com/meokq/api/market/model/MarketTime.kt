package com.meokq.api.market.model

import com.meokq.api.core.enums.TypeYN
import com.meokq.api.core.model.BaseModel
import com.meokq.api.market.enums.WeekDay
import com.meokq.api.market.model.identifier.MarketTimeId
import jakarta.persistence.*

@Entity(name = "tb_market_time")
@IdClass(MarketTimeId::class)
class MarketTime(
    @Id
    @Enumerated(EnumType.STRING)
    var weekDay : WeekDay? = null,
    @Id
    var marketId : String? = null,
    var openTime: String? = null,
    var closeTime: String? = null,
    @Enumerated(EnumType.STRING)
    var holidayYn : TypeYN = TypeYN.N,
) : BaseModel()