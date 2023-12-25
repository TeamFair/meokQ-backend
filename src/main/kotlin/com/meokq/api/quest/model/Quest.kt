package com.meokq.api.quest.model

import com.meokq.api.core.model.BaseModel
import com.meokq.api.quest.enums.QuestStatus
import com.meokq.api.quest.request.QuestCreateReq
import com.meokq.api.quest.request.QuestReq
import jakarta.persistence.*
import org.hibernate.annotations.UuidGenerator
import java.time.LocalDateTime

@Entity(name = "tb_quest")
class Quest(
    @Id
    @UuidGenerator
    var questId : String? = null,
    @Enumerated(EnumType.STRING)
    var status : QuestStatus = QuestStatus.UNDER_REVIEW,
    var marketId : String? = null,

    var expireDate : LocalDateTime? = null,

   @OneToMany(mappedBy = "questId", cascade = [], orphanRemoval = true)
    var missions: List<Mission>? = null,

    @OneToMany(mappedBy = "questId", cascade = [], orphanRemoval = true)
    var rewards: List<Reward>? = null,

) : BaseModel(){
    @Deprecated("아래 생성자를 사용. (QuestReq 사용 지양)")
    constructor(req : QuestReq) : this(
        marketId = req.marketId,
        missions = req.missions.map { Mission(it) },
        rewards = req.rewards.map { Reward(it) },
    )

    constructor(req: QuestCreateReq) : this(
        marketId = req.marketId,
        missions = req.missions.map { Mission(it) },
        rewards = req.rewards.map { Reward(it) },
    )
}