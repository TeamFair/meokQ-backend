package com.meokq.api.challenge.model

import com.meokq.api.challenge.enums.ChallengeStatus
import com.meokq.api.core.model.BaseModel
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import org.hibernate.annotations.UuidGenerator

@Entity(name = "tb_challenge_history")
data class Challenge(
    @Id
    @UuidGenerator
    var challengeId : String? = null,
    var customerId : String? = null,

    @Enumerated(EnumType.STRING)
    var status : ChallengeStatus = ChallengeStatus.UNDER_REVIEW,
    var rejectReason : String? = null,
    var questId : String? = null,
    val receiptImage : String? = null,
) : BaseModel()
