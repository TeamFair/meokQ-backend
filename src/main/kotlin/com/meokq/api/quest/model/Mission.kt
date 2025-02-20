package com.meokq.api.quest.model

import com.meokq.api.answer.model.AnswerEntity
import com.meokq.api.core.model.BaseModel
import com.meokq.api.quest.enums.MissionType
import com.meokq.api.quest.request.MissionReq
import jakarta.annotation.Nullable
import jakarta.persistence.*
import org.hibernate.annotations.UuidGenerator
import org.hibernate.internal.util.collections.CollectionHelper.listOf

@Entity(name = "tb_mission")
class Mission(
    @Id
    @UuidGenerator
    var missionId: String? = null,
    var questId: String? = null,
    var quantity: Int? = null,
    @Nullable
    var target: String?,
    var content: String? = null,
    @Enumerated(EnumType.STRING)
    var type : MissionType? = null,

    // 2025-02-16 OX, 단답형에 대해 질문을 저장
    var question : String? = null,

    // 정답처리 할수 있는 정답 리스트
    @OneToMany
    @JoinColumn(name = "mission_id")
    var answers: List<AnswerEntity> = listOf(),

) : BaseModel(){
    constructor(req : MissionReq) : this(
        content = req.content,
        target = req.target,
        quantity = req.quantity,
        type = req.type
    )

    constructor(req: MissionReq, questId: String) : this(
        content = req.content,
        target = req.target,
        quantity = req.quantity,
        type = req.type,
        questId = questId
    )

}
