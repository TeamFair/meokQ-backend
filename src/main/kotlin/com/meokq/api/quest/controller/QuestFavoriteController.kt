package com.meokq.api.quest.controller

import com.meokq.api.core.AuthDataProvider
import com.meokq.api.core.ResponseEntityCreation
import com.meokq.api.core.dto.BaseListRespV2
import com.meokq.api.core.dto.BaseResp
import com.meokq.api.quest.annotations.*
import com.meokq.api.quest.enums.QuestTarget
import com.meokq.api.quest.request.QuestCreateReq
import com.meokq.api.quest.request.QuestCreateReqForAdmin
import com.meokq.api.quest.request.QuestSearchDto
import com.meokq.api.quest.request.QuestUpdateReq
import com.meokq.api.quest.service.QuestFavoriteService
import com.meokq.api.quest.service.QuestService
import com.meokq.api.quiz.service.QuizService
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

@Tag(name = "Quest", description = "퀘스트")
@RestController
@RequestMapping("/api")
class QuestFavoriteController(
    private val service : QuestFavoriteService,
) : ResponseEntityCreation, AuthDataProvider {

    @ExplainQuestFavoriteSaveQuest
    @PostMapping(value = ["/customer/quest/{questId}/favorite"])
    @Transactional(rollbackFor = [Exception::class])
    fun save(@PathVariable questId: String): ResponseEntity<BaseResp> {
        service.save(questId, getAuthReq())
        return getRespEntity(null)
    }

    @ExplainQuestFavoriteDeleteQuest
    @DeleteMapping(value = ["/customer/quest/{questId}/favorite"])
    @Transactional(rollbackFor = [Exception::class])
    fun delete(@PathVariable questId: String) : ResponseEntity<BaseResp> {
        return getRespEntity(service.delete(questId, getAuthReq()))
    }

}
