package com.meokq.api.application.request

import org.jetbrains.annotations.NotNull

class BossRequest(
    @NotNull
    val email : String,
    val nickName : String
)
