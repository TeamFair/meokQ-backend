package com.meokq.api.banner.dto

import com.meokq.api.banner.BannerEntity
import com.meokq.api.file.response.ImageResp

data class BannerCreateResponse(
    val bannerId : Long?,
    val image: ImageResp?,
){
    companion object {
        fun of(banner: BannerEntity): BannerCreateResponse {
            return BannerCreateResponse(
                bannerId = banner.id,
                image = banner.image?.let { ImageResp(it) }
            )
        }
    }

}