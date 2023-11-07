package com.meokq.api.application.model

import com.meokq.api.application.enums.ChallengeStatus
import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.hibernate.annotations.UuidGenerator
import java.time.LocalDateTime

@Entity(name = "tb_challenge_history")
data class Challenge(
    @Id
    @UuidGenerator
    var challengeId : String? = null,
    var customerId : String? = null,

    var status : ChallengeStatus = ChallengeStatus.UNDER_REVIEW,
    var rejectReason : String? = null,
    @CreationTimestamp
    var createDate : LocalDateTime? = null,
    @UpdateTimestamp
    var updateDate : LocalDateTime? = null,
    var questId : String? = null,
    val receiptImage : String? = null,
)
