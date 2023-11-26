package com.meokq.api.challenge.repository

import com.meokq.api.challenge.enums.ChallengeStatus
import com.meokq.api.challenge.model.Challenge
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional

interface ChallengeRepository : JpaRepository<Challenge, String> {
    @Query("SELECT c FROM tb_challenge_history c JOIN tb_quest q ON c.questId = q.questId WHERE q.marketId = :marketId AND c.status = :status")
    fun findAllByMarketIdAndStatus(
        @Param("marketId") marketId: String,
        @Param("status") status: ChallengeStatus,
        pageable: Pageable
    ): List<Challenge>
    @Transactional // 없으면 오류 발생
    @Modifying
    @Query("UPDATE tb_challenge_history c SET c.rejectReason = :rejectReason, c.status = :status WHERE c.challengeId = :challengeId")
    fun updateRejectReasonAndStatus(challengeId: String, status: ChallengeStatus, rejectReason: String?): Int

    @Query("SELECT count(c.challengeId) FROM tb_challenge_history c JOIN tb_quest q ON c.questId = q.questId WHERE q.marketId = :marketId AND c.challengeId = :challengeId")
    fun countByChallengeIdAndMarketId(
        @Param("challengeId") challengeId: String,
        @Param("marketId") marketId: String,
    ) : Int
}