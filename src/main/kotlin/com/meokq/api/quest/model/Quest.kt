package com.meokq.api.quest.model

import com.meokq.api.auth.enums.UserType
import com.meokq.api.core.model.BaseModelV2
import com.meokq.api.quest.enums.QuestStatus
import com.meokq.api.quest.enums.QuestTarget
import com.meokq.api.quest.enums.QuestType
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

    var score: Int = 0,
    @Enumerated(EnumType.STRING)
    var target: QuestTarget,
    @Enumerated(EnumType.STRING)
    var type : QuestType,
    ) : BaseModelV2(){

    fun addImageId(imageId: String) {
        this.imageId = imageId
    }

    fun softDelete() {
        this.status = QuestStatus.DELETED
        this.expireDate = LocalDateTime.now()
    }

    fun refreshFields(req: Quest){
        writer = req.writer
        imageId = req.imageId
        missions = req.missions
        rewards = req.rewards
        expireDate = req.expireDate
        score = req.score
        type = req.type
        target = req.target
    }

}
