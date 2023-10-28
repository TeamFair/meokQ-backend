package com.meokq.api.application.entity

import com.meokq.api.application.enums.QuestStatus
import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.jetbrains.annotations.NotNull

@Entity(name = "tb_quest")
class TbQuest : BaseModel() {
    @Id
    var questId : String? = null
    @NotNull
    var questStatus : QuestStatus = QuestStatus.UNDER_REVIEW
    @NotNull
    var marketId : String? = null
}