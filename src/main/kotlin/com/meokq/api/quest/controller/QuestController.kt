package com.meokq.api.quest.controller

import com.meokq.api.core.AuthDataProvider
import com.meokq.api.core.ResponseEntityCreation
import com.meokq.api.core.dto.BaseListRespV2
import com.meokq.api.core.dto.BaseResp
import com.meokq.api.file.enums.ImageType
import com.meokq.api.file.request.ImageReq
import com.meokq.api.quest.annotations.*
import com.meokq.api.quest.request.QuestCreateReq
import com.meokq.api.quest.request.QuestCreateReqForAdmin
import com.meokq.api.quest.request.QuestSearchDto
import com.meokq.api.quest.service.QuestService
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@Tag(name = "Quest", description = "퀘스트")
@RestController
@RequestMapping("/api")
class QuestController(
    private val service : QuestService,
) : ResponseEntityCreation, AuthDataProvider {

    @ExplainSelectQuestList
    @GetMapping(value = ["/open/quest","/admin/quest"])
    fun findAll(
        searchDto: QuestSearchDto,
        @RequestParam(defaultValue = "0") page : Int,
        @RequestParam(defaultValue = "10") size : Int,
    ) : ResponseEntity<BaseListRespV2> {
        // TODO : 사용자별 필수값 차이

        val result = service.findAll(
            searchDto = searchDto,
            pageable = PageRequest.of(page, size),
        )
        return getListRespEntity(result)
    }

    @ExplainSelectQuest
    @GetMapping(value = ["/open/quest/{questId}"])
    fun findById(@PathVariable questId: String): ResponseEntity<BaseResp> {
        return getRespEntity(service.findById(questId))
    }

    @ExplainSaveQuest
    @PostMapping(value = ["/boss/quest"])
    @Transactional(rollbackFor = [Exception::class])
    fun saveQuest(
        @RequestBody @Valid request: QuestCreateReq,
        ): ResponseEntity<BaseResp> {
        return getRespEntity(service.save(request))
    }

    @ExplainSaveQuest
    @PostMapping(value = ["/admin/quest" ])
    @Transactional(rollbackFor = [Exception::class])
    fun saveQuestAdmin(
        @RequestBody @Valid request: QuestCreateReqForAdmin
    ): ResponseEntity<BaseResp> {
        return getRespEntity(service.adminSave(
            request = request
        ))
    }


    @ExplainCompletedQuests
    @GetMapping(value = ["/customer/completedQuest"])
    fun findCompletedQuests(
        @RequestParam(defaultValue = "0") page : Int,
        @RequestParam(defaultValue = "10") size : Int,
        ): ResponseEntity<BaseListRespV2> {
        return getListRespEntity(service.getCompletedQuests(
            pageable = PageRequest.of(page, size),
            authReq = getAuthReq())
        )
    }

    @ExplainUncompletedQuests
    @GetMapping(value = ["/customer/uncompletedQuest"])
    fun findUncompletedQuests(
        @RequestParam(defaultValue = "0") page : Int,
        @RequestParam(defaultValue = "10") size : Int,
        ): ResponseEntity<BaseListRespV2> {
        return getListRespEntity(service.getUncompletedQuests(
            pageable = PageRequest.of(page, size),
            authReq = getAuthReq())
        )
    }

    @ExplainDeleteQuest
    @DeleteMapping(value = ["/admin/quest"])
    @Transactional(rollbackFor = [Exception::class])
    fun delete(
        @RequestParam questId : String,
        ) : ResponseEntity<BaseResp> {
        return getRespEntity(service.delete(questId))
    }



}