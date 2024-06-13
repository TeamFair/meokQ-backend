package com.meokq.api.quest.model

import com.meokq.api.core.model.BaseModelV2
import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.hibernate.annotations.UuidGenerator

@Entity(name = "tb_questHistory")
class QuestHistory (
    @Id
    @UuidGenerator
    var questHistoryId : String? = null,

    var questId: String? = null,

    var customerId: String? = null

): BaseModelV2() {
    constructor(quest: String, customer: String) : this (
        questId = quest,
        customerId = customer
    )
}