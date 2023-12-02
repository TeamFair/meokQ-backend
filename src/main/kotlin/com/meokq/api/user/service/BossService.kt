package com.meokq.api.user.service

import com.meokq.api.core.converter.BaseConverter
import com.meokq.api.user.model.Boss
import com.meokq.api.core.exception.NotFoundException
import com.meokq.api.core.exception.NotUniqueException
import com.meokq.api.core.service.BaseService
import com.meokq.api.user.converter.BossConverter
import com.meokq.api.user.repository.BossRepository
import com.meokq.api.user.request.BossReq
import com.meokq.api.user.response.BossResp
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
