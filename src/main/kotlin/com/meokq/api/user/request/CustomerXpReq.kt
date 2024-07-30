package com.meokq.api.user.request

import com.meokq.api.emoji.enums.TargetType
import com.meokq.api.logs.processor.UserAction
import org.jetbrains.annotations.NotNull

data class CustomerXpReq(
    @NotNull
    val xpPoint : Long,
    val title: String,
    val targetType: TargetType? = null,
    val targetId: String? = null,
){
    constructor(userAction: UserAction) : this(
        xpPoint = userAction.xpPoint,
        title = userAction.title
    )
}
