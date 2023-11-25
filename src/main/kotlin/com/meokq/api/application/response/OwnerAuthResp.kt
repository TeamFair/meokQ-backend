package com.meokq.api.application.response

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate
@Schema(name = "Owner-Auth-Response")
data class OwnerAuthResp(
    @Schema(description = "이름")
    val name: String?,

    @Schema(description = "생년월일")
    val birthdate: LocalDate?,

    @Schema(description = "신분증 이미지")
    val idcardImage: ImageResp?,
)