package com.meokq.api.xp.repository

import com.meokq.api.core.repository.BaseRepository
import com.meokq.api.user.model.Customer
import com.meokq.api.xp.model.Xp

interface XpRepository : BaseRepository<Xp, String> {
    fun deleteALlByCustomer(customer: Customer)
}