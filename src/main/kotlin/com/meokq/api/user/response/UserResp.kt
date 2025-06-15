package com.meokq.api.user.response

import com.meokq.api.user.enums.UserStatus
import com.meokq.api.user.model.Boss
import com.meokq.api.user.model.Customer

class UserResp(
    val userId: String?,
    val status: UserStatus,
    val email: String? = null,
) {
    constructor(model: Customer) : this(
        userId = model.customerId,
        status = model.status,
        email = model.email,
    )

    constructor(model: Boss) : this(
        userId = model.bossId,
        status = model.status,
        email = model.email,
    )
}
