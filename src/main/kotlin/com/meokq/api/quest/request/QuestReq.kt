package com.meokq.api.quest.request

import io.swagger.v3.oas.annotations.media.Schema

@Deprecated("요청값을 용도에 맞게 세분화.")
@Schema(name = "Quest-Request")
class QuestReq(
    @Schema(example = "MK00000001")
    val marketId : String, // TODO : 추후 제거
    val missions : List<MissionReq>,
    val rewards : List<RewardReq>
)