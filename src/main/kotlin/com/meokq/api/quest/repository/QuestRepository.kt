package com.meokq.api.quest.repository

import com.meokq.api.quest.model.Quest
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface QuestRepository : JpaRepository<Quest, String>, JpaSpecificationExecutor<Quest> {
    //fun findAll(spec: Specification<Quest>?, pageable: Pageable?): Page<Quest>
}