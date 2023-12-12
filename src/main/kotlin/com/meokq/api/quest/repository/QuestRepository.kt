package com.meokq.api.quest.repository

import com.meokq.api.quest.model.Quest
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.jpa.repository.JpaRepository

interface QuestRepository : JpaRepository<Quest, String> {
    fun findAll(spec: Specification<Quest>?, pageable: Pageable?): Page<Quest>
}