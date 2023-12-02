package com.meokq.api.auth.response

import com.meokq.api.user.response.AgreementResp
import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "Auth-Response")
data class AuthResp(
    @Schema(description = "JWT token for authorization")
    var authorization: String? = null,
    var agreements : List<AgreementResp> = mutableListOf()
)
