package com.meokq.api.application.model

import java.io.Serializable

class ChallengeId(
    var challengeOrder : Long? = null,
    var questId : String? = null,
    var customerId : String? = null,
) : Serializable