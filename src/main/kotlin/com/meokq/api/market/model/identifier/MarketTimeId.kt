package com.meokq.api.market.model.identifier

import com.meokq.api.market.enums.WeekDay
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import java.io.Serializable

data class MarketTimeId(
    @Enumerated(EnumType.STRING)
    var weekDay : WeekDay? = null,
    var marketId : String? = null,
) : Serializable