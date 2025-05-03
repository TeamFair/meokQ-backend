package com.meokq.api.title.repository

import com.meokq.api.title.response.TitleHistoryRankResp
import com.meokq.api.title.response.TitleHistoryResp

interface TitleHistoryCustomRepository {
    fun findAllForUnRead(userId: String): List<TitleHistoryResp>
    fun findAllByCustomerId(userId: String): List<TitleHistoryResp>
    fun findAllByRank(titleId: String): List<TitleHistoryRankResp>
}
