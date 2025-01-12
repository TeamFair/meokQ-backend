package com.meokq.api.banner

import com.meokq.api.core.enums.TypeYN

data class BannerUpdateRequest(
    val title: String? = null,
    val description: String? = null,
    val activeYn: TypeYN? = null,
)
