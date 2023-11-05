package com.meokq.api.application.repository

import com.meokq.api.application.enums.UserType
import com.meokq.api.application.model.Notice
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository


interface NoticeRepository : JpaRepository<Notice, String> {
    fun findByTarget(target: UserType, pageable: Pageable): Page<Notice>
}