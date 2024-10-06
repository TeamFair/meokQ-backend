package com.meokq.api.user.response

import com.meokq.api.user.model.Customer
import com.meokq.api.xp.model.XpType

class XpRankCustomerResp(
    customer: Customer, val xpType: XpType, val xpPoint: Long
){
    val customerId: String? = customer.customerId
    val nickname: String = customer.nickname?: ""
}
