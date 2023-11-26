package com.meokq.api.image.repository

import com.meokq.api.image.model.Image
import org.springframework.data.jpa.repository.JpaRepository

interface ImageRepository : JpaRepository<Image, String> {
}