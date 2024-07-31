package com.meokq.api.xp.model

import com.meokq.api.core.model.BaseModel
import com.meokq.api.core.model.TargetMetadata
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity(name = "tb_xp_history")
class XpHistory(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var recordId: Long? = null,
    var description: String? = null,
    var title: String? = null,
    var xpPoint: Long = 0,
    @Embedded
    var targetMetadata: TargetMetadata
): BaseModel() {
}