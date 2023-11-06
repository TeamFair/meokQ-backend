package com.meokq.api.application.repository

import com.meokq.api.application.model.Boss
import org.springframework.data.jpa.repository.JpaRepository

interface BossRepository : JpaRepository<Boss, String> {
    fun findBossByEmail(token : String) : Boss?
}