package com.meokq.api.user.response

import com.meokq.api.auth.enums.AuthChannel
import com.meokq.api.user.enums.UserStatus
import com.meokq.api.user.model.Boss
import com.meokq.api.user.model.Customer

data class WithdrawResp(
    val email : String?,
    val status : UserStatus?,
    val channel: AuthChannel?,
) {
    constructor(model: Boss) : this(
        email = model.email,
        status = model.status,
        channel = model.channel,
    )

    constructor(model: Customer) : this(
        email = model.email,
        status = model.status,
        channel = model.channel,
    )
}
