package com.meokq.api.notice.repository

import com.meokq.api.notice.enums.NoticeTarget
import com.meokq.api.notice.model.Notice
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository


interface NoticeRepository : JpaRepository<Notice, String> {
    fun findByTarget(target: NoticeTarget, pageable: Pageable): Page<Notice>
}