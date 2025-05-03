package com.meokq.api.title.repository

import com.meokq.api.title.model.TitleHistory
import org.springframework.data.jpa.repository.JpaRepository

interface TitleHistoryRepository : JpaRepository<TitleHistory, String>, TitleHistoryCustomRepository
