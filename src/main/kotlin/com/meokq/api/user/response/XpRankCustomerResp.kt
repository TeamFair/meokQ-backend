package com.meokq.api.user.response

import com.meokq.api.title.model.Title
import com.meokq.api.title.response.TitleResp
import com.meokq.api.user.model.Customer
import com.meokq.api.xp.model.XpType

class XpRankCustomerResp(
    customer: Customer, val xpType: XpType, val xpPoint: Long, val title: Title?
) {
    val customerId: String? = customer.customerId
    val nickname: String = customer.nickname ?: ""
    val profileImage: String? = customer.profileImageId
    val titleInfo: TitleResp? = title?.let { TitleResp(title) }
}
