package com.meokq.api.user.request

import com.meokq.api.xp.processor.UserAction
import org.jetbrains.annotations.NotNull

data class CustomerXpReq(
    @NotNull
    val xpPoint : Long,
    val title: String,
){
    constructor(userAction: UserAction) : this(
        xpPoint = userAction.xpPoint,
        title = userAction.title,
    )
}
