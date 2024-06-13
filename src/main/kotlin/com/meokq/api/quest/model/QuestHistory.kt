package com.meokq.api.quest.model

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
)