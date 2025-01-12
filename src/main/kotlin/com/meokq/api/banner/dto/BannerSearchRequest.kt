package com.meokq.api.banner.dto

import com.meokq.api.core.enums.TypeYN

data class BannerSearchRequest(
    var titleLike: String? = null,
    var descriptionLike: String? = null,
    var activeYn: TypeYN? = null,
)
