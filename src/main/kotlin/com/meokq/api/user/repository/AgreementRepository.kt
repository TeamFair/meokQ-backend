package com.meokq.api.user.repository

import com.meokq.api.user.model.Agreement
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.jpa.repository.JpaRepository

interface AgreementRepository : JpaRepository<Agreement, String> {
    fun findAll(spec: Specification<Agreement>?, pageable: Pageable): Page<Agreement>
}