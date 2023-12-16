package com.meokq.api.quest.controller

import com.meokq.api.auth.enums.UserType
import com.meokq.api.core.controller.BaseController
import com.meokq.api.core.dto.BaseListRespV2
import com.meokq.api.core.dto.BaseResp
import com.meokq.api.core.service.BaseService
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
            Parameter(name = "page", description = "페이지 번호", required = false),
            Parameter(name = "size", description = "페이지 크기", required = false)
        ]
    )
    @GetMapping
    fun findAll(
        searchDto: QuestSearchDto,
        @RequestParam(defaultValue = "0") page : Int,
        @RequestParam(defaultValue = "10") size : Int,
    ) : ResponseEntity<BaseListRespV2> {
        // TODO : 사용자별 필수값 차이

        val result = service.findAll(
            searchDto = searchDto,
            pageable = PageRequest.of(page, size)
        )
        return getListRespEntityV2(result)
    }

    @Operation(
        summary = "Quest 상세정보 조회",
        description = "조건에 맞는 모든 Quest 상세정보를 조회합니다.",
        parameters = [
            Parameter(name = "page", description = "페이지 번호", required = false),
            Parameter(name = "size", description = "페이지 크기", required = false)
        ]
    )
    @GetMapping("/{questId}")
    override fun findById(@PathVariable questId: String): ResponseEntity<BaseResp> {
        checkAccess(isOpen = true)
        return super.findById(questId)
    }

    @Operation(
        summary = "Quest 저장",
        description = "새로운 Quest를 저장합니다."
    )
    @PostMapping
    override fun save(@Valid request: QuestReq): ResponseEntity<BaseResp> {
        checkAccess(listOf(UserType.BOSS))
        return super.save(request)
    }

    @Operation(
        summary = "quest 정보 삭제",
        description = """
            Boss는 검토중인 quest만 삭제할 수 있습니다.
            Admin은 검토중인 quest만 삭제할 수 있습니다. 
            Customer은 quest를 삭제할 수 없습니다.
        """,
        parameters = [
            Parameter(name = "questId", description = "quest 아이디", required = true),
        ]
    )
    @DeleteMapping("/{questId}")
    override fun deleteById(@PathVariable questId: String) : ResponseEntity<BaseResp> {
        checkAccess(listOf(UserType.BOSS, UserType.ADMIN))
        return super.deleteByIdWithAuth(questId)
    }
}