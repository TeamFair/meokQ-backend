package com.meokq.api.application.model

import com.meokq.api.application.enums.TypeYN
import com.meokq.api.application.enums.WeekDay
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import java.time.LocalTime

@IdClass(MarketTimeId::class)
@Entity(name = "tb_market_time")
class MarketTime(
    @Id
    @Enumerated(EnumType.STRING)
    var weekDay : WeekDay? = null,
    @Id
    var marketId : String? = null,
    var openTime: LocalTime? = null,
    var closeTime: LocalTime? = null,
    @Enumerated(EnumType.STRING)
    var holidayYn : TypeYN = TypeYN.N,
    @CreationTimestamp
    var createDate : LocalDateTime? = null,
    @UpdateTimestamp
    var updateDate : LocalDateTime? = null,
)