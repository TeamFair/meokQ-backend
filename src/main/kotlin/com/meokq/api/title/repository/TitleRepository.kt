package com.meokq.api.title.repository

import com.meokq.api.title.model.Title
import org.springframework.data.jpa.repository.JpaRepository

interface TitleRepository : JpaRepository<Title, String> {
}
