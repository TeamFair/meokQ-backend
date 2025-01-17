package com.meokq.api.banner.repository

import com.meokq.api.banner.BannerUpdateRequest
import com.meokq.api.banner.dto.BannerItemResponse
import com.meokq.api.banner.dto.BannerSearchRequest
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest

interface BannerDslRepository {
    fun findAllBySearchRequest(searchRequest: BannerSearchRequest, pageable: PageRequest): PageImpl<BannerItemResponse>
    fun updateByBannerId(request: BannerUpdateRequest, bannerId: Long): Int
}