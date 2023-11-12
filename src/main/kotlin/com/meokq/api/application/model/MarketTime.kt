package com.meokq.api.application.model

import com.meokq.api.application.enums.TypeYN
import com.meokq.api.application.enums.WeekDay
import com.meokq.api.application.model.identifier.MarketTimeId
import com.meokq.api.core.model.BaseModel
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