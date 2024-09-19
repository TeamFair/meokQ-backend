package com.meokq.api.quest.request

import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.web.multipart.MultipartFile

class QuestCreateReq(
    @Schema(example = "MK00000001")
    val marketId : String, // TODO : 추후 제거
    val missions : List<MissionReq>,
    val rewards : List<RewardReq>,
    val score : Int = 0
    /*@Schema(description = "만료 시간")
    @field:Pattern(regexp = "\\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])", message = "날짜 형식은 yyyy-MM-dd 이어야 합니다.")
    val expireDate : String? = null*/
)