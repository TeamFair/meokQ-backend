package com.meokq.api.market.request

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate

@Schema(name = "Owner-Auth-Request")
data class OwnerAuthReq(
    @Schema(description = "이름")
    val name: String,

    @Schema(description = "생년월일")
    val birthdate: LocalDate,

    @Schema(description = "신분증 이미지 Id")
    val idcardImageId: String,
)