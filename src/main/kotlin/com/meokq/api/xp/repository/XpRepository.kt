package com.meokq.api.xp.repository

import com.meokq.api.core.repository.BaseRepository
import com.meokq.api.user.model.Customer
import com.meokq.api.xp.model.Xp
import com.meokq.api.xp.model.XpType

interface XpRepository : BaseRepository<Xp, String> {
    fun deleteAllByCustomer(customer: Customer)

    fun findByCustomerAndXpType(customer: Customer, xpType: XpType): Xp?
}