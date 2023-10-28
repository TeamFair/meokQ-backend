package com.meokq.api.application.repository

import com.meokq.api.application.model.Notice
import org.springframework.data.jpa.repository.JpaRepository

interface NoticeRepository : JpaRepository<Notice, String> {
}