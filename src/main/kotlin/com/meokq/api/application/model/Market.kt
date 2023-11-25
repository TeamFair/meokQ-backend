package com.meokq.api.application.model

import com.meokq.api.application.enums.MarketStatus
import com.meokq.api.core.model.BaseModel
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import org.hibernate.annotations.UuidGenerator

@Entity(name = "tb_market")
class Market(
    @Id
    @UuidGenerator
    var marketId : String? = null,

    var name : String? = null,
    var address : String? = null,
    var district : String? = null,
    var content : String? = null,
    var phone : String? = null,
    var ticketCount : Int? = 0,
    var logoImageId : String? = null,

    @Enumerated(EnumType.STRING)
    var status : MarketStatus = MarketStatus.UNDER_REVIEW,

    var presidentId : String? = null,
) : BaseModel()