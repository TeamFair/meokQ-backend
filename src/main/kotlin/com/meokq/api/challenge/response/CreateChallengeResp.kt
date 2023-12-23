package com.meokq.api.challenge.response

import com.meokq.api.challenge.model.Challenge

class CreateChallengeResp(
    model : Challenge
){
    val challengeId : String? = model.challengeId
}
