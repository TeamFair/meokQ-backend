package com.meokq.api.banner

import com.meokq.api.banner.annotations.ExplainCreateBanner
import com.meokq.api.banner.annotations.ExplainDeleteBanner
import com.meokq.api.banner.annotations.ExplainSelectBanner
import com.meokq.api.banner.annotations.ExplainUpdateBanner
import com.meokq.api.banner.dto.BannerCreateRequest
import com.meokq.api.banner.dto.BannerCreateRequestV2
import com.meokq.api.banner.dto.BannerSearchRequest
import com.meokq.api.core.AuthDataProvider
import com.meokq.api.core.ResponseEntityCreation
import com.meokq.api.core.dto.BaseListRespV2
import com.meokq.api.core.dto.BaseResp
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@Tag(name = "Banner", description = "배너")
@RequestMapping("/api")
@RestController
class BannerController(
    private val bannerService: BannerService,
): ResponseEntityCreation, AuthDataProvider{

    /*@PostMapping("/admin/v2/banners/new", consumes = ["multipart/form-data"])
    fun createBannerMultipart(
        @RequestPart("image") image: MultipartFile,
        @RequestParam request: BannerCreateRequest,
    ): ResponseEntity<BaseResp> {
        val result = bannerService.createBannerWithImage(image, request)
        return getRespEntity(result)
    }*/

    @ExplainCreateBanner
    @PostMapping(value = ["/admin/v1/banners/new"])
    fun createBanner(
        @RequestBody request: BannerCreateRequestV2,
    ): ResponseEntity<BaseResp> {
        val result = bannerService.createBanner(request)
        return getRespEntity(result)
    }

    @ExplainUpdateBanner
    @PutMapping(value = ["/admin/v1/banners/{bannerId}"])
    fun updateBanner(
        @Valid @RequestBody request: BannerUpdateRequest,
        @PathVariable bannerId: Long,
    ): ResponseEntity<BaseResp> {
        return getRespEntity(bannerService.update(request, bannerId))
    }

    @ExplainSelectBanner
    @GetMapping(value = ["/open/v1/banners"])
    fun selectBanner(
        searchRequest: BannerSearchRequest,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
    ): ResponseEntity<BaseListRespV2> {
        val responses = bannerService.findAll(searchRequest, PageRequest.of(page, size))
        return getListRespEntity(responses)
    }

    @ExplainDeleteBanner
    @DeleteMapping(value = ["/admin/v1/banners/{bannerId}"])
    fun deleteBanner(
        @PathVariable bannerId: Long,
    ): ResponseEntity<BaseResp> {
        return getRespEntity(bannerService.deleteById(bannerId, getAuthReq()))
    }
}
