package com.meokq.api.quest.model

import com.meokq.api.core.model.BaseModel
import com.meokq.api.quest.enums.QuestStatus
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import org.hibernate.annotations.UuidGenerator

@Entity(name = "tb_quest")
class Quest(
    @Id
    @UuidGenerator
    var questId : String? = null,
    @Enumerated(EnumType.STRING)
    var questStatus : QuestStatus = QuestStatus.UNDER_REVIEW,
    var marketId : String? = null, // Market model 의 id 와 연결되는 외부 키
) : BaseModel()