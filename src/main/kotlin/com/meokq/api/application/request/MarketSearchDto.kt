package com.meokq.api.application.request

import org.springframework.data.domain.Pageable

class MarketSearchDto(
    val district : String?,
    val pageable: Pageable
)