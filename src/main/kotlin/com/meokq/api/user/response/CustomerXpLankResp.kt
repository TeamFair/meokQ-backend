package com.meokq.api.user.response

data class CustomerXpLankResp(
    val nickname: String,
    val xpSum: Long,
    val profileImage: String?,
    val lank: Int,
)
