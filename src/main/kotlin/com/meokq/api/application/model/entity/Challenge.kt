package com.meokq.api.application.model.entity

import com.meokq.api.application.enums.ChallengeStatus
import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity(name = "tb_challenge_history")
class Challenge {
    @Id
    var sequence : Int? = null
    @Id
    var questId : Int? = null
    @Id
    var customerId : String? = null

    var status : ChallengeStatus? = null
}