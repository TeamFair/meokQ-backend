package com.meokq.api.answer.model

import org.springframework.data.jpa.repository.JpaRepository

interface AnswerRepository : JpaRepository<AnswerEntity, String> {
    fun findByQuizQuizIdIn(quizIds: List<String>): List<AnswerEntity>
}