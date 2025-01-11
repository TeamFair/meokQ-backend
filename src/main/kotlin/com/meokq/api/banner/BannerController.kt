package com.meokq.api.banner

import com.meokq.api.banner.dto.BannerCreateRequest
import com.meokq.api.banner.dto.BannerCreateRequestV2
import com.meokq.api.core.AuthDataProvider
import com.meokq.api.core.ResponseEntityCreation
import com.meokq.api.core.dto.BaseResp
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@Tag(name = "Banner", description = "배너")
@RequestMapping("/api")
@RestController
class BannerController(
    private val bannerService: BannerService,
): ResponseEntityCreation, AuthDataProvider{

    @PostMapping("/admin/v2/banners/new", consumes = ["multipart/form-data"])
    fun createBannerMultipart(
        @RequestPart("image") image: MultipartFile,
        @RequestParam request: BannerCreateRequest,
    ): ResponseEntity<BaseResp> {
        val result = bannerService.createBannerWithImage(image, request)
        return getRespEntity(result)
    }

    @PostMapping("/admin/v1/banners/new")
    fun createBannerMultipart(
        @RequestParam request: BannerCreateRequestV2,
    ): ResponseEntity<BaseResp> {
        val result = bannerService.createBanner(request)
        return getRespEntity(result)
    }

    @PutMapping("/admin/v1/banners/{bannerId}")
    fun updateBanner(
        @Valid @RequestBody request: BannerUpdateRequest,
        @PathVariable bannerId: Long,
    ): ResponseEntity<BaseResp> {
        return getRespEntity(bannerService.update(request, bannerId))
    }

    @DeleteMapping("/admin/v1/banners/{bannerId}")
    fun deleteBanner(
        @PathVariable bannerId: Long,
    ): ResponseEntity<BaseResp> {
        return getRespEntity(bannerService.deleteById(bannerId, getAuthReq()))
    }
}
