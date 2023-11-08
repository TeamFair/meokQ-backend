package com.meokq.api.application.model

import com.meokq.api.application.enums.MissionType
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.hibernate.annotations.UuidGenerator
import java.time.LocalDateTime

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
    @CreationTimestamp
    var createDate: LocalDateTime? = null,
    @UpdateTimestamp
    var updateDate: LocalDateTime? = null
)