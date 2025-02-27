package com.meokq.api.quest.model

import com.meokq.api.answer.model.AnswerEntity
import com.meokq.api.core.model.BaseModel
import com.meokq.api.quest.enums.MissionType
import com.meokq.api.quest.request.MissionReq
import com.meokq.api.quiz.model.QuizEntity
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
    @OneToMany(mappedBy = "mission", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var quizzes: MutableList<QuizEntity> = mutableListOf(),
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
