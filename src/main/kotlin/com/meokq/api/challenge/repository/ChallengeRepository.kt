package com.meokq.api.challenge.repository

import com.meokq.api.challenge.model.Challenge
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ChallengeRepository : JpaRepository<Challenge, String> {
    @Query("SELECT c FROM tb_challenge_history c JOIN tb_quest q ON c.questId = q.questId")
    fun findAll(spec: Specification<Challenge>?, pageable: Pageable?): Page<Challenge>

}