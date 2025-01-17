package com.meokq.api.banner.repository

import com.meokq.api.banner.BannerEntity
import com.meokq.api.file.model.Image
import org.springframework.data.jpa.repository.JpaRepository

interface BannerRepository : JpaRepository<BannerEntity, Long>, BannerDslRepository {
    fun existsByImage(image: Image): Boolean
}