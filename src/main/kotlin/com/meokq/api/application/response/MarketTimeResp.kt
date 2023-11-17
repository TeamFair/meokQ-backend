package com.meokq.api.application.response

import com.meokq.api.application.enums.TypeYN
import com.meokq.api.application.enums.WeekDay
import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "Market-Time-Response")
data class MarketTimeResp(
    @Schema(description = "요일")
    var weekDay: WeekDay?,

    @Schema(description = "시작 시간")
    var openTime: String?,

    @Schema(description = "종료 시간")
    var closeTime: String?,

    @Schema(description = "휴뮤일 여부")
    var holidayYn : TypeYN,
)
