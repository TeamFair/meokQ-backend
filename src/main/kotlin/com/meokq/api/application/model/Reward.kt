package com.meokq.api.application.model

import com.meokq.api.application.enums.RewardType
import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.hibernate.annotations.UuidGenerator
import java.time.LocalDateTime

@Entity(name = "tb_reward")
class Reward (
    @Id
    @UuidGenerator
    var rewardId : String? = null,
    var questId : String? = null,
    var quantity : Int? = null,
    var discountRate : Int? = null,
    var content : String? = null,
    var target : String? = null,
    var type : RewardType? = null,
    @CreationTimestamp
    var createDate : LocalDateTime? = null,
    @UpdateTimestamp
    var updateDate : LocalDateTime? = null
)