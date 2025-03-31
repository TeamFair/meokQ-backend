package com.meokq.api.quest.model

import com.meokq.api.auth.enums.UserType
import com.meokq.api.core.model.BaseModel
import com.meokq.api.core.model.BaseModelV2
import com.meokq.api.quest.enums.QuestStatus
import com.meokq.api.quest.enums.QuestTarget
import com.meokq.api.quest.enums.QuestType
import jakarta.persistence.*
import jakarta.validation.ValidationException
import org.hibernate.annotations.ColumnDefault
import org.hibernate.annotations.UuidGenerator
import java.time.LocalDateTime

@Entity
@Table(name = "tb_quest_favorite")
class QuestFavorite(
    @Id
    @UuidGenerator
    var id: String? = null,
    var customerId: String,
    var questId: String,
) : BaseModel()
