package com.meokq.api.xp.repository

import com.meokq.api.core.repository.BaseRepository
import com.meokq.api.xp.model.XpHistory

interface XpHisRepository: BaseRepository<XpHistory, String> {
    fun deleteByTargetIdAndUserId(targetId:String, userId: String)
    fun findByTargetId(targetId:String): XpHistory?

}