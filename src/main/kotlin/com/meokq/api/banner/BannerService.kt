package com.meokq.api.banner

import com.meokq.api.auth.request.AuthReq
import com.meokq.api.banner.dto.*
import com.meokq.api.banner.repository.BannerRepository
import com.meokq.api.file.repository.ImageRepository
import com.meokq.api.file.service.ImageService
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Transactional(readOnly = true)
@Service
class BannerService(
    private val bannerRepository: BannerRepository,
    private val imageRepository: ImageRepository,
    private val imageService: ImageService,
) {
    @Transactional
    fun createBannerWithImage(image: MultipartFile, request: BannerCreateRequest): Unit? {
        return null
    }

    @Transactional
    fun createBanner(request: BannerCreateRequestV2): BannerCreateResponse {
        val image = imageRepository.findById(request.imageId)
            .orElseThrow { IllegalArgumentException("등록되지 않은 이미지입니다.") }

        val existsByImage = bannerRepository.existsByImage(image)
        if (existsByImage) throw IllegalArgumentException("같은 아이디로 이미 등록된 배너가 있습니다.")

        val saveBanner = bannerRepository.save(BannerEntity.create(request, image))
        return BannerCreateResponse.of(saveBanner)
    }

    fun findAll(bannerSearchRequest: BannerSearchRequest, pageable: PageRequest): PageImpl<BannerItemResponse> {
        return bannerRepository.findAllBySearchRequest(bannerSearchRequest, pageable)
    }

    @Transactional
    fun update(request: BannerUpdateRequest, bannerId: Long) {
        bannerRepository.updateByBannerId(request, bannerId)
    }

    @Transactional
    fun deleteById(bannerId: Long, authReq: AuthReq) {
        val findBanner = bannerRepository.findById(bannerId)
            .orElseThrow { IllegalArgumentException("등록되지 않는 배너입니다.") }
        val fileId = findBanner.image?.fileId

        // 1. 배너-이미지 관계를 끊음
        findBanner.image = null
        bannerRepository.save(findBanner)

        // 2. 배너 삭제
        bannerRepository.deleteById(bannerId)

        // 3. 이미지 삭제
        fileId?.let { imageService.deleteById(it, authReq) }
    }


}