package com.meokq.api.title.service

import com.meokq.api.core.JpaService
import com.meokq.api.title.model.Title
import com.meokq.api.title.repository.TitleRepository
import com.meokq.api.title.response.TitleAdminResp
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class TitleService(
    val repository: TitleRepository,
): JpaService<Title, String> {
    override var jpaRepository: JpaRepository<Title, String> = repository

    fun findAll(): List<TitleAdminResp> = repository.findAll().map { TitleAdminResp(it) }
}
