package com.meokq.api.quest.request

import com.meokq.api.quest.enums.MissionType
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.ValidationException

@Schema(name = "Mission-Request")
class MissionReq(
    @Schema(description = "미션 설명(자유형식)", example = "10회 이상 방문")
    val content: String?,

    @Schema(description = "미션 대상", example = "")
    val target: String?,

    @Schema(description = "미션 수량", example = "")
    val quantity: Int?,

    @Schema(description = "미션 종류", example = "FREE")
    val type: MissionType
){
    fun validate(){
        when (type){
            MissionType.NORMAL -> {
                if (target.isNullOrBlank()) throw ValidationException("미션 대상은 비어있을 수 없습니다.")
                if (quantity == null || !(1..100).contains(quantity)) throw ValidationException("미션수량은 0~100 사이의 수여야 합니다.")
            }
            MissionType.FREE -> {
                if (content.isNullOrBlank()) throw ValidationException("미션 설명(자유형식)은 비어있을 수 없습니다.")
            }
            else -> {
                //
            }
        }
    }
}