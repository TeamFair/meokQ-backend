package com.meokq.api.quest.controller

import com.meokq.api.core.controller.BaseController
import com.meokq.api.core.dto.BaseListResp
import com.meokq.api.core.dto.BaseResp
import com.meokq.api.core.service.BaseService
import com.meokq.api.quest.enums.QuestStatus
import com.meokq.api.quest.model.Quest
import com.meokq.api.quest.request.QuestReq
import com.meokq.api.quest.request.QuestSearchDto
import com.meokq.api.quest.response.QuestResp
import com.meokq.api.quest.service.QuestService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.data.domain.PageRequest
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
        description = "조건에 맞는 모든 Quest 목록을 조회합니다.",
        parameters = [
            Parameter(name = "marketId", description = "마켓 ID", required = true),
            Parameter(name = "page", description = "페이지 번호", required = false),
            Parameter(name = "size", description = "페이지 크기", required = false)
        ]
    )
    @GetMapping
    fun findAll(
        @RequestParam(required = false) marketId : String?,
        @RequestParam(required = false) questId : String?,
        @RequestParam(required = false) questStatus : QuestStatus?,
        @RequestParam(defaultValue = "0") page : Int,
        @RequestParam(defaultValue = "10") size : Int,
    ) : ResponseEntity<BaseListResp<QuestResp>> {
        val result = service.findAll(
            searchDto = QuestSearchDto(
                marketId = marketId,
                questId = questId,
                questStatus = questStatus,
            ),
            pageable = PageRequest.of(page, size)
        )
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

    @Operation(
        summary = "quest 정보 삭제",
        description = "quest 정보를 삭제합니다.",
        parameters = [
            Parameter(name = "questId", description = "quest 아이디", required = true),
        ]
    )
    @DeleteMapping("/{marketId}")
    override fun deleteById(@PathVariable questId: String) : ResponseEntity<BaseResp> {
        return super.deleteById(questId)
    }
}