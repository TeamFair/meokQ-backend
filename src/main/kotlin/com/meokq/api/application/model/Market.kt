package com.meokq.api.application.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.Parameter
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@Entity(name = "tb_market")
class Market(
    @Id
    @GenericGenerator(
        name = "market_id_gen",
        strategy = "com.meokq.api.core.idGenerator.CustomIdGenerator",
        parameters = [
            Parameter(name = "sequenceName", value = "seq_market"),
            Parameter(name = "prefix", value = "MK"),
        ]
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "market_id_gen")
    var marketId : String? = null,

    var name : String? = null,
    var address : String? = null,
    var district : String? = null,
    var content : String? = null,
    var phone : String? = null,
    var ticketCount : Int? = 0,
    var logoImage : String? = null,

    @CreationTimestamp
    var createDate : LocalDateTime? = null,
    @UpdateTimestamp
    var updateDate : LocalDateTime? = null,
)