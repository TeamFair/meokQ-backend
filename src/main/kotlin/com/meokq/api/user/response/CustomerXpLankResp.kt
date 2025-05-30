package com.meokq.api.user.response

import com.meokq.api.title.model.Title
import com.meokq.api.title.response.TitleResp

data class CustomerXpLankResp(
    val customerId: String,
    val nickname: String,
    val xpSum: Long,
    val profileImage: String?,
    val lank: Int,
    val title: TitleResp?,
) {
    constructor(
        customerId: String,
        nickname: String,
        xpSum: Long,
        profileImage: String?,
        lank: Int,
        title: Title?,
    ) : this(
        customerId = customerId,
        nickname = nickname,
        xpSum = xpSum,
        profileImage = profileImage,
        lank = lank,
        title = title?.let { TitleResp(title) }
    )
}
