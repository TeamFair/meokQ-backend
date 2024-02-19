package com.meokq.api.quest.model

import com.meokq.api.core.model.BaseModel
import com.meokq.api.quest.enums.QuestStatus
import com.meokq.api.quest.request.QuestCreateReq
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

   @OneToMany(mappedBy = "questId", cascade = [CascadeType.MERGE, CascadeType.PERSIST], fetch = FetchType.LAZY)
    var missions: List<Mission>? = null,

    @OneToMany(mappedBy = "questId", cascade = [CascadeType.MERGE, CascadeType.PERSIST], fetch = FetchType.LAZY)
    var rewards: List<Reward>? = null,

) : BaseModel(){

    constructor(req: QuestCreateReq) : this(
        marketId = req.marketId,
        missions = req.missions.map { Mission(it) },
        rewards = req.rewards.map { Reward(it) },
    )
}