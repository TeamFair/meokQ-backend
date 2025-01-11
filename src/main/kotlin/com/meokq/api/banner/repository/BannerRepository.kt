package com.meokq.api.banner.repository

import com.meokq.api.banner.BannerEntity
import org.springframework.data.jpa.repository.JpaRepository

interface BannerRepository : JpaRepository<BannerEntity, Long>, BannerDslRepository {
}