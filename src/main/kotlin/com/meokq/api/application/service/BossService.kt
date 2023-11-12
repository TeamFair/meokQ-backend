package com.meokq.api.application.service

import com.meokq.api.application.converter.BaseConverter
import com.meokq.api.application.converter.BossConverter
import com.meokq.api.application.model.Boss
import com.meokq.api.application.repository.BossRepository
import com.meokq.api.application.request.BossReq
import com.meokq.api.application.response.BossResp
import com.meokq.api.core.exception.NotFoundException
import com.meokq.api.core.exception.NotUniqueException
import com.meokq.api.core.service.BaseService
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service

@Service
class BossService (
    val converter: BossConverter,
    val repository: BossRepository
) : BaseService<BossReq, BossResp, Boss, String> {
    override var _converter: BaseConverter<BossReq, BossResp, Boss> = converter
    override var _repository: JpaRepository<Boss, String> = repository

    fun findByEmail(email: String): BossResp {
        val result = repository.findBossByEmail(email) ?: throw NotFoundException()
        return converter.modelToResponse(result)
    }

    override fun save(request: BossReq): BossResp {
        if (repository.findBossByEmail(request.email) != null) {
            throw NotUniqueException()
        }
        return super.save(request)
    }
}
