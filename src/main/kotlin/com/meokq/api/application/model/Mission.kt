package com.meokq.api.application.model

import com.meokq.api.application.enums.MissionType
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import java.util.*

@Entity(name = "tb_mission")
class Mission(
    @Id
    @GeneratedValue
    var missionId: UUID? = null,
    var questId: String? = null,
    var quantity: Int? = null,
    var target: String? = null,
    var content: String? = null,
    var type : MissionType? = null,
    @CreationTimestamp
    var createDate: LocalDateTime? = null,
    @UpdateTimestamp
    var updateDate: LocalDateTime? = null
)