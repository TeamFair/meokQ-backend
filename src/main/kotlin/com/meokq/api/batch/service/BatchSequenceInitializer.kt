package com.meokq.api.batch.service

import jakarta.annotation.PostConstruct
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service


@Service
class BatchSequenceInitializer(private val jdbcTemplate: JdbcTemplate) {
    @PostConstruct
    fun initializeSequence() {
        // 가장 큰 ID 값 가져오기
        val maxId = jdbcTemplate.queryForObject(
            "SELECT COALESCE(MAX(id), 0) FROM BATCH_JOB_SEQ", Long::class.java
        )

        // 시퀀스를 다음 값으로 설정
        jdbcTemplate.update("ALTER TABLE BATCH_JOB_SEQ AUTO_INCREMENT = ?", maxId!! + 1)
    }
}