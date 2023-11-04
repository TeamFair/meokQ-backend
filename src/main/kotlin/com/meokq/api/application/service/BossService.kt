package com.meokq.api.application.service

import com.meokq.api.application.model.Boss
import com.meokq.api.application.repository.BossRepository
import com.meokq.api.application.request.BossRequest
import com.meokq.api.application.response.BossResponse
import com.meokq.api.core.converter.BaseConverter
import com.meokq.api.core.converter.BossConverter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class BossService @Autowired constructor(
    private val converter: BossConverter,
    private val repository: BossRepository
) : BaseService<BossRequest, BossResponse, Boss, UUID> {
    override var _converter: BaseConverter<BossRequest, BossResponse, Boss> = converter
    override var _repository: JpaRepository<Boss, UUID> = repository
}
