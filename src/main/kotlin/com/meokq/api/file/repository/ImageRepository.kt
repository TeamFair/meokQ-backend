package com.meokq.api.file.repository

import com.meokq.api.file.model.Image
import org.springframework.data.jpa.repository.JpaRepository

interface ImageRepository : JpaRepository<Image, String> {
}