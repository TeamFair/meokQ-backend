package com.meokq.api.application.controller

import com.meokq.api.application.model.Quest
import com.meokq.api.application.request.QuestReq
import com.meokq.api.application.response.QuestResp
import com.meokq.api.application.service.QuestService
import com.meokq.api.core.controller.BaseController
import com.meokq.api.core.dto.BaseListResp
import com.meokq.api.core.dto.BaseResp
import com.meokq.api.core.service.BaseService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "Quest", description = "퀘스트")
@RestController
@RequestMapping("/api/quests")
class QuestController(
    val service : QuestService
) : BaseController<QuestReq, QuestResp, Quest, String> {
    override val _service: BaseService<QuestReq, QuestResp, Quest, String> = service

    @Operation(
        summary = "Quest 목록 조회",
        description = "지정한 마켓 ID에 대한 모든 Quest 목록을 조회합니다.",
        parameters = [
            Parameter(name = "marketId", description = "마켓 ID", required = true),
            Parameter(name = "page", description = "페이지 번호", required = false),
            Parameter(name = "size", description = "페이지 크기", required = false)
        ]
    )
    @GetMapping
    fun findAllByMarketId(
        @RequestParam marketId : String,
        @RequestParam(defaultValue = "0") page : Int,
        @RequestParam(defaultValue = "10") size : Int,
    ) : ResponseEntity<BaseListResp<QuestResp>> {
        val pageable: Pageable = PageRequest.of(page, size)
        val result = service.findAllByMarketId(marketId, pageable)
        return ResponseEntity.ok(
            BaseListResp(
            content = result.content,
            totalElements = result.totalElements,
            size = result.size,
            number = result.number
        )
        )
    }

    @Operation(
        summary = "Quest 저장",
        description = "새로운 Quest를 저장합니다."
    )
    @PostMapping
    override fun save(@Valid request: QuestReq): ResponseEntity<BaseResp> {
        return super.save(request)
    }
}