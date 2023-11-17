package com.meokq.api.application.repository

import com.meokq.api.application.model.Image
import org.springframework.data.jpa.repository.JpaRepository

interface ImageRepository : JpaRepository<Image, String> {
}