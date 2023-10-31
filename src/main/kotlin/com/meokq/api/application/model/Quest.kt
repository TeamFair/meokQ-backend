package com.meokq.api.application.model

import com.meokq.api.application.enums.QuestStatus
import jakarta.persistence.*
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.Parameter
import org.jetbrains.annotations.NotNull

@Entity(name = "tb_quest")
class Quest(
    @Id
    @GenericGenerator(
        name = "quest_id_gen",
        strategy = "com.meokq.api.core.idGenerator.CustomIdGenerator",
        parameters = [
            Parameter(name = "sequenceName", value = "seq_quest"),
            Parameter(name = "prefix", value = "QS"),
        ]
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quest_id_gen")
    var questId : String? = null,
    @NotNull
    var questStatus : QuestStatus = QuestStatus.UNDER_REVIEW,
    @NotNull
    var marketId : String? = null,
)