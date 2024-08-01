package com.meokq.api.user.request

import com.meokq.api.core.model.TargetMetadata
import com.meokq.api.xp.processor.UserAction
import org.jetbrains.annotations.NotNull

data class CustomerXpReq(
    @NotNull
    val xpPoint : Long,
    val title: String,
    val targetMetadata: TargetMetadata,
){
    constructor(userAction: UserAction, targetMetadata: TargetMetadata) : this(
        xpPoint = userAction.xpPoint,
        title = userAction.title,
        targetMetadata = targetMetadata
    )
}
