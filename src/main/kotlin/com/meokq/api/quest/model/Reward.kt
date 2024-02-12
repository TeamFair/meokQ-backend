package com.meokq.api.quest.model

import com.meokq.api.core.model.BaseModel
import com.meokq.api.quest.enums.RewardType
import com.meokq.api.quest.request.RewardReq
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
    var questId : String? = null, // Quest model 의 id 와 연결되는 외부 키
    var quantity : Int? = null,
    var discountRate : Int? = null,
    var content : String? = null,
    var target : String? = null,
    @Enumerated(EnumType.STRING)
    var type : RewardType? = null,
) : BaseModel() {
    constructor(req : RewardReq) : this(
        content = req.content,
        target = req.target,
        quantity = req.quantity,
        discountRate = req.discountRate,
        type = req.type
    )

    constructor(req: RewardReq, questId: String) : this(
        content = req.content,
        target = req.target,
        quantity = req.quantity,
        discountRate = req.discountRate,
        type = req.type,
        questId = questId,
    )
}