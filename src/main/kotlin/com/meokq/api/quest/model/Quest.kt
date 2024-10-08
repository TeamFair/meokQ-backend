package com.meokq.api.quest.model

import com.meokq.api.auth.enums.UserType
import com.meokq.api.core.model.BaseModelV2
import com.meokq.api.quest.enums.QuestStatus
import com.meokq.api.quest.request.*
import jakarta.persistence.*
import org.hibernate.annotations.UuidGenerator
import java.time.LocalDate
import java.time.LocalDateTime

@Entity(name = "tb_quest")
class Quest(
    @Id
    @UuidGenerator
    var questId : String? = null,
    @Enumerated(EnumType.STRING)
    var status : QuestStatus = QuestStatus.UNDER_REVIEW,

    var imageId : String? = null,

    var marketId : String? = null,

    /* 240707
    admin 유저가 퀘스트 생성시 생성자 이름을 커스텀 하기 위한 필드
    * */
    var writer : String? = null,

    var expireDate : LocalDateTime? = null,

    @OneToMany(mappedBy = "questId", cascade = [CascadeType.REMOVE], fetch = FetchType.LAZY)
    var missions: List<Mission>? = null,

    @OneToMany(mappedBy = "questId", cascade = [CascadeType.REMOVE], fetch = FetchType.LAZY)
    var rewards: List<Reward>? = null,

    @Enumerated(EnumType.STRING)
    var creatorRole : UserType = UserType.UNKNOWN,

    var score: Int = 0

    ) : BaseModelV2(){

    constructor(req: QuestCreateReq) : this(
        marketId = req.marketId,
        missions = req.missions.map { Mission(it) },
        rewards = req.rewards.map { Reward(it) },
    )

    constructor(req: QuestCreateReqForAdmin) : this(
        missions = req.missions.map { Mission(it) },
        rewards = req.rewards.map { Reward(it) },
        creatorRole = UserType.ADMIN,
        writer = req.writer,
        expireDate = LocalDate.parse(req.expireDate).atTime(0, 0,0 ),
        status = QuestStatus.PUBLISHED,
        score = req.score
    )

    fun addImageId(imageId: String) {
        this.imageId = imageId
    }

    fun softDelete() {
        this.status = QuestStatus.DELETED
        this.expireDate = LocalDateTime.now()
    }

    fun refreshFields(req: QuestUpdateReq){
        writer = req.writer
        imageId = req.imageId
        missions = req.missions.map { Mission(it) }.toMutableList()
        rewards = req.rewards.map { Reward(it) }.toMutableList()
        expireDate = LocalDate.parse(req.expireDate).atTime(0, 0,0 )
        score = req.score
    }

}