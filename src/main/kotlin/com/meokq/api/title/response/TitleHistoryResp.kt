package com.meokq.api.title.response

import com.meokq.api.title.model.Title
import com.meokq.api.title.model.TitleHistory
import com.meokq.api.user.model.Customer
import com.meokq.api.user.response.CustomerResp
import java.time.LocalDateTime

data class TitleHistoryResp(
    val titleHistory: TitleHistoryDetailResp?,
    val title: TitleResp?,
) {
    constructor(titleHistory: TitleHistory?, title: Title?) : this(
        titleHistory = titleHistory?.let { TitleHistoryDetailResp(titleHistory) },
        title = title?.let { TitleResp(title) },
    )
}

data class TitleHistoryDetailResp(
    val id: String?,
    val createdAt : LocalDateTime?,
) {
    constructor(titleHistory: TitleHistory) : this(
        id = titleHistory.id,
        createdAt = titleHistory.createDate,
    )
}

data class TitleHistoryRankResp(
    val customer: CustomerResp,
    val titleHistory: TitleHistoryDetailResp,
) {
    constructor(customer: Customer, titleHistory: TitleHistory, title: Title, totalXp: Long) : this(
        customer = CustomerResp(customer, title, totalXp),
        titleHistory = TitleHistoryDetailResp(titleHistory),
    )
}
