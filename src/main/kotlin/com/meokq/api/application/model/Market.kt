package com.meokq.api.application.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.hibernate.annotations.UuidGenerator
import java.time.LocalDateTime

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
    var logoImage : String? = null,

    var presidentId : String? = null,

    @CreationTimestamp
    var createDate : LocalDateTime? = null,
    @UpdateTimestamp
    var updateDate : LocalDateTime? = null,
)