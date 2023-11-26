package com.meokq.api.quest.model

import com.meokq.api.core.model.BaseModel
import com.meokq.api.quest.enums.RewardType
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import org.hibernate.annotations.UuidGenerator

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
    @Enumerated(EnumType.STRING)
    var type : RewardType? = null,
) : BaseModel()