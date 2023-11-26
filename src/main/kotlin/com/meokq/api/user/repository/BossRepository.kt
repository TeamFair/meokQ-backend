package com.meokq.api.user.repository

import com.meokq.api.user.model.Boss
import org.springframework.data.jpa.repository.JpaRepository

interface BossRepository : JpaRepository<Boss, String> {
    fun findBossByEmail(token : String) : Boss?
}