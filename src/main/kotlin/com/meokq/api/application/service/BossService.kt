package com.meokq.api.application.service

import com.meokq.api.application.model.Boss
import com.meokq.api.application.repository.BossRepository
import com.meokq.api.application.request.BossRequest
import com.meokq.api.application.response.BossResponse
import com.meokq.api.core.converter.BaseConverter
import com.meokq.api.core.converter.BossConverter
import com.meokq.api.core.exception.NotFoundException
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service

@Service
class BossService (
    val converter: BossConverter,
    val repository: BossRepository
) : BaseService<BossRequest, BossResponse, Boss, String> {
    override var _converter: BaseConverter<BossRequest, BossResponse, Boss> = converter
    override var _repository: JpaRepository<Boss, String> = repository

    fun getBossByEmail(email: String): BossResponse {
        val result = repository.findBossByEmail(email) ?: throw NotFoundException()
        return converter.modelToResponse(result)
    }
}
