package com.meokq.api.application.model

import com.meokq.api.application.enums.ChallengeStatus
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.Parameter
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@IdClass(ChallengeId::class)
@Entity(name = "tb_challenge_history")
data class Challenge(
    @Id
    @GenericGenerator(
        name = "challenge_id_gen",
        strategy = "com.meokq.api.core.idGenerator.OrderIdGenerator",
        parameters = [
            Parameter(name = "tableName", value = "tb_challenge_history"),
            Parameter(name = "whereColumnNames", value = "questId,customerId")
        ]
    )
    @GeneratedValue(generator = "challenge_id_gen")
    var challengeOrder : Long? = null,
    @Id
    var questId : String? = null,
    @Id
    var customerId : String? = null,

    var status : ChallengeStatus = ChallengeStatus.UNDER_REVIEW,
    @CreationTimestamp
    var createDate : LocalDateTime? = null,
    @UpdateTimestamp
    var updateDate : LocalDateTime? = null,
)
