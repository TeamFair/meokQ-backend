package com.meokq.api.xp.model

import com.meokq.api.core.enums.TargetType
import com.meokq.api.core.model.BaseModel
import com.meokq.api.core.model.TargetMetadata
import jakarta.persistence.*

@Entity(name = "tb_xp_history")
class XpHistory(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var recordId: Long? = null,
    var description: String? = null,
    var title: String? = null,
    var xpPoint: Long = 0,


    @Enumerated(EnumType.STRING)
    val targetType: TargetType,
    val targetId: String,
    val userId: String
): BaseModel() {
    constructor(xpPoint: Long, title: String, targetMetadata: TargetMetadata): this(
        recordId = null,
        description = null,
        title = title,
        xpPoint = xpPoint,
        targetType = targetMetadata.targetType,
        targetId = targetMetadata.targetId,
        userId = targetMetadata.userId
    )
}