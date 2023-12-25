package com.meokq.api.quest.request

import io.swagger.v3.oas.annotations.media.Schema

class QuestCreateReq(
    @Schema(example = "MK00000001")
    val marketId : String, // TODO : 추후 제거
    val missions : List<MissionReq>,
    val rewards : List<RewardReq>
){

}