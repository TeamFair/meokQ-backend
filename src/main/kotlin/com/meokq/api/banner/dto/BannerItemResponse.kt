package com.meokq.api.banner.dto

import com.meokq.api.core.enums.TypeYN
import com.meokq.api.file.response.ImageResp

data class BannerItemResponse(
    val id: Long?,
    val title: String?,
    val description: String?,
    val image: ImageResp?,
    val activeYn: TypeYN?,
)
