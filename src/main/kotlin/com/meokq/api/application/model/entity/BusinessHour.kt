package com.meokq.api.application.model.entity

import com.meokq.api.application.enums.DayWeek
import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.time.LocalTime

@Entity(name = "tb_business_hour")
class BusinessHour : BaseModel() {
    @Id
    var marketId : String? = null
    @Id
    var dayWeek : DayWeek? = null
    @Id
    var sequence : Int? = null

    var openTime : LocalTime? = null
    var closeTime : LocalTime? = null
}