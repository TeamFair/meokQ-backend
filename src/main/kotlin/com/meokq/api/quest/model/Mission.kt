package com.meokq.api.quest.model

import com.meokq.api.answer.model.AnswerEntity
import com.meokq.api.core.model.BaseModel
import com.meokq.api.quest.enums.MissionType
import com.meokq.api.quest.request.MissionReq
import jakarta.annotation.Nullable
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import org.hibernate.annotations.UuidGenerator

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

    @OneToOne
    @JoinColumn(name = "answer_id")
    var answer: AnswerEntity? = null

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
