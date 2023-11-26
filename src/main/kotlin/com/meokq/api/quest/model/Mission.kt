package com.meokq.api.quest.model

import com.meokq.api.core.model.BaseModel
import com.meokq.api.quest.enums.MissionType
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import org.hibernate.annotations.UuidGenerator

@Entity(name = "tb_mission")
class Mission(
    @Id
    @UuidGenerator
    var missionId: String? = null,
    var questId: String? = null,
    var quantity: Int? = null,
    var target: String? = null,
    var content: String? = null,
    @Enumerated(EnumType.STRING)
    var type : MissionType? = null,
) : BaseModel()