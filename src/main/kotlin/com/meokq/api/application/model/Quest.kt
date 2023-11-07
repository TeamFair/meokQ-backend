package com.meokq.api.application.model

import com.meokq.api.application.enums.QuestStatus
import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.hibernate.annotations.UuidGenerator
import org.jetbrains.annotations.NotNull

@Entity(name = "tb_quest")
class Quest(
    @Id
    @UuidGenerator
    var questId : String? = null,
    @NotNull
    var questStatus : QuestStatus = QuestStatus.UNDER_REVIEW,
    var marketId : String? = null,
)