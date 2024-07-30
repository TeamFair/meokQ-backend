package com.meokq.api.logs.repository

import com.meokq.api.core.repository.BaseRepository
import com.meokq.api.logs.model.XpHistory

interface XpHisRepository: BaseRepository<XpHistory, String> {
    fun deleteByTitle(title :String)
}