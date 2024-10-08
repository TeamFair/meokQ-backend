package com.meokq.api.xp.model

import com.meokq.api.core.model.BaseModel
import com.meokq.api.xp.processor.UserAction
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
    val userId: String

) : BaseModel() {
    constructor(userAction: UserAction, userId: String) : this(
        title = userAction.title,
        xpPoint = userAction.xpPoint,
        userId = userId
    )

}