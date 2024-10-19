package com.meokq.api.quest.model

import com.meokq.api.core.model.BaseDateTimeModel
import com.meokq.api.core.model.BaseModelV2
import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.hibernate.annotations.UuidGenerator

@Entity(name = "tb_quest_history")
class QuestHistory (
    @Id
    @UuidGenerator
    var questHistoryId : String? = null,

    var questId: String? = null,

    var customerId: String? = null

): BaseDateTimeModel() {
    constructor(quest: String, customer: String) : this (
        questId = quest,
        customerId = customer
    )

}