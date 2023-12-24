package com.meokq.api.challenge.controller

import com.meokq.api.challenge.model.Challenge
import com.meokq.api.challenge.request.ChallengeReq
import com.meokq.api.challenge.request.ChallengeSaveReq
import com.meokq.api.challenge.request.ChallengeSearchDto
import com.meokq.api.challenge.response.ChallengeResp
import com.meokq.api.challenge.response.CreateChallengeResp
import com.meokq.api.challenge.service.ChallengeService
import com.meokq.api.core.controller.BaseController
import com.meokq.api.core.dto.BaseListRespV2
import com.meokq.api.core.dto.BaseResp
import com.meokq.api.core.enums.ErrorStatus
import com.meokq.api.core.service.BaseService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "Challenge", description = "도전 내역")
@RestController
class ChallengeController(
    private val service : ChallengeService
) : BaseController<ChallengeReq, ChallengeResp, Challenge, String> {
    override val _service: BaseService<ChallengeReq, ChallengeResp, Challenge, String> = service

    @Operation(
        summary = "(ICH003) 도전내역 조회",
        description = "마켓에서 등록한 진행상태별 도전내역을 조회합니다.",
    )
    @GetMapping(value = [
        "/api/customer/challenge",
        "/api/boss/challenge"
    ])
    @ApiResponse(
        responseCode = "200",
        description = "성공",
        content = [Content(
            mediaType = "application/json",
            schema = Schema(implementation = BaseResp::class),
            examples = [ExampleObject(value = """
                {
                  "size": 2,
                  "data": [
                    {
                      "challengeId": "CH10000004",
                      "quest": {
                        "questId": "QS10000002",
                        "marketId": "MK00000002",
                        "missions": [
                          {
                            "content": null,
                            "target": "COFFEE",
                            "quantity": 3,
                            "type": "NORMAL"
                          }
                        ],
                        "rewards": [
                          {
                            "content": null,
                            "target": "DONUT",
                            "quantity": 1,
                            "discountRate": null,
                            "type": "GIFT"
                          }
                        ]
                      },
                      "receiptImageId": "IM10000003",
                      "status": "UNDER_REVIEW"
                    },
                    {
                      "challengeId": "CH10000002",
                      "quest": {
                        "questId": "QS10000002",
                        "marketId": "MK00000002",
                        "missions": [
                          {
                            "content": null,
                            "target": "COFFEE",
                            "quantity": 3,
                            "type": "NORMAL"
                          }
                        ],
                        "rewards": [
                          {
                            "content": null,
                            "target": "DONUT",
                            "quantity": 1,
                            "discountRate": null,
                            "type": "GIFT"
                          }
                        ]
                      },
                      "receiptImageId": "IM10000002",
                      "status": "UNDER_REVIEW"
                    }
                  ],
                  "total": 2,
                  "page": 0,
                  "status": "OK",
                  "message": "Your request has been processed successfully."
                }
            """)])]
    )
    fun findAll(
        searchDto: ChallengeSearchDto,
        @RequestParam(defaultValue = "0") page : Int,
        @RequestParam(defaultValue = "10") size : Int,
    ) : ResponseEntity<BaseListRespV2> {
        val result = service.findAll(
            searchDto = searchDto,
            pageable = PageRequest.of(page, size),
            authReq = getAuthReq()
        )

        return getListRespEntityV2(result)
    }

    @Operation(
        summary = "(ICH001) 도전 내역 등록",
        description = "사용자앱에서 제출하기 버튼을 눌렀을 때, 정보를 바디에 담아서 호출해주세요.",
    )
    @PostMapping(value = [
        "/api/customer/challenge",
    ])
    @ApiResponse(
        responseCode = "200",
        description = "성공",
        content = [Content(
            mediaType = "application/json",
            schema = Schema(implementation = BaseResp::class),
            examples = [ExampleObject(value = """
                {
                  "data": {
                    "challengeId": "CH00000100"
                  },
                  "status": "OK",
                  "message": "Your request has been processed successfully."
                }
            """)])]
    )
    fun save(
        @RequestBody @Valid request: ChallengeSaveReq
    ): ResponseEntity<BaseResp> {
        val saveData = service.save(request, getAuthReq())
        return getRespEntity(CreateChallengeResp(saveData), ErrorStatus.CREATED)
    }

    @Operation(
        summary = "(ICH006) 도전 내역 세부정보 삭제",
        description = "도전 내역 세부정보를 삭제합니다. (검토중일때만 삭제할 수 있습니다.)",
        parameters = [
            Parameter(name = "challengeId", description = "도전내역 아이디", required = true, example = "CH10000004"),
        ]
    )
    @ApiResponse(
        responseCode = "200",
        description = "성공",
        content = [Content(
            mediaType = "application/json",
            schema = Schema(implementation = BaseResp::class),
            examples = [ExampleObject(value = """
                {
                  "data": {},
                  "status": "OK",
                  "message": "Your request has been processed successfully."
                }
            """)])]
    )
    @DeleteMapping("/api/customer/{challengeId}")
    override fun deleteById(@PathVariable challengeId: String) : ResponseEntity<BaseResp> {
        return super.deleteByIdWithAuth(challengeId)
    }
}