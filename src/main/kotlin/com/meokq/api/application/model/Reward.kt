package com.meokq.api.application.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import java.util.UUID

@Entity(name = "tb_reward")
class Reward (
    @Id
    @GeneratedValue
    var rewardId : UUID? = null,
    var questId : String? = null,
    var quantity : Int? = null,
    var discountRate : Int? = null,
    var content : String? = null,
    var target : String? = null,
    @CreationTimestamp
    var createDate : LocalDateTime? = null,
    @UpdateTimestamp
    var updateDate : LocalDateTime? = null
)