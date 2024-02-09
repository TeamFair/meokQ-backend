package com.meokq.api.quest.controller

import com.meokq.api.core.ResponseEntityCreation
import com.meokq.api.core.dto.BaseListRespV2
import com.meokq.api.core.dto.BaseResp
import com.meokq.api.quest.annotations.ExplainSaveQuest
import com.meokq.api.quest.annotations.ExplainSelectQuest
import com.meokq.api.quest.annotations.ExplainSelectQuestList
import com.meokq.api.quest.request.QuestCreateReq
import com.meokq.api.quest.request.QuestSearchDto
import com.meokq.api.quest.service.QuestService
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "Quest", description = "퀘스트")
@RestController
@RequestMapping("/api")
class QuestController(
    private val service : QuestService
) : ResponseEntityCreation {

    @ExplainSelectQuestList
    @GetMapping(value = ["/open/quest"])
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
        return getListRespEntity(result)
    }

    @ExplainSelectQuest
    @GetMapping(value = ["/open/quest/{questId}"])
    fun findById(@PathVariable questId: String): ResponseEntity<BaseResp> {
        return getRespEntity(service.findQuestById(questId))
    }

    @ExplainSaveQuest
    @PostMapping(value = ["/boss/quest", ])
    fun saveQuest(
        @RequestBody @Valid request: QuestCreateReq
    ): ResponseEntity<BaseResp> {
        return getRespEntity(service.saveQuest(request))
    }
}