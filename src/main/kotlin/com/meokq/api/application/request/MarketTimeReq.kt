package com.meokq.api.application.request

import com.fasterxml.jackson.annotation.JsonIgnore
import com.meokq.api.application.enums.TypeYN
import com.meokq.api.application.enums.WeekDay
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Pattern

@Schema(name = "MarketTime-Request")
data class MarketTimeReq(
    @Schema(description = "요일")
    var weekDay: WeekDay,

    @JsonIgnore
    var marketId : String? = null,

    // TODO : 영업 시작/종료 시작 형식 지정
    @Schema(description = "시작 시간")
    @field:Pattern(regexp = "^([0-1][0-9]|2[0-3]):[0-5][0-9]$", message = "시간 형식은 HH:mm:ss여야 합니다.")
    var openTime: String,

    @Schema(description = "종료 시간")
    @field:Pattern(regexp = "^([0-1][0-9]|2[0-3]):[0-5][0-9]$", message = "시간 형식은 HH:mm:ss여야 합니다.")
    var closeTime: String,

    @Schema(description = "휴뮤일 여부")
    var holidayYn : TypeYN,
)
