package com.meokq.api.market.service

import com.meokq.api.core.converter.BaseConverter
import com.meokq.api.market.converter.MarketAuthConverter
import com.meokq.api.market.model.MarketAuth
import com.meokq.api.market.repository.MarketAuthRepository
import com.meokq.api.market.request.MarketAuthReq
import com.meokq.api.market.reposone.MarketAuthResp
import com.meokq.api.core.service.BaseService
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service

@Service
class MarketAuthService(
    private final val repository: MarketAuthRepository,
    private final val converter: MarketAuthConverter,
) : BaseService<MarketAuthReq, MarketAuthResp, MarketAuth, String> {
    override var _repository: JpaRepository<MarketAuth, String> = repository
    override var _converter: BaseConverter<MarketAuthReq, MarketAuthResp, MarketAuth> = converter
}