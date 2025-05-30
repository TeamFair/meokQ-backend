package com.meokq.api.title.controller

import com.meokq.api.core.AuthDataProvider
import com.meokq.api.core.ResponseEntityCreation
import com.meokq.api.core.dto.BaseResp
import com.meokq.api.title.annotations.ExplainSelectTitleHistory
import com.meokq.api.title.annotations.ExplainSelectTitleHistoryRank
import com.meokq.api.title.annotations.ExplainSelectTitleHistoryUnRead
import com.meokq.api.title.annotations.ExplainUpdateTitleHistoryForRead
import com.meokq.api.title.service.TitleHistoryService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Tag(name = "TitleHistory", description = "칭호 획득이력")
@Controller
@RequestMapping("/api")
class TitleHistoryController(
    private val service: TitleHistoryService,
): ResponseEntityCreation, AuthDataProvider {

    @ExplainUpdateTitleHistoryForRead
    @PutMapping("/customer/title/history/{titleHistoryId}/read")
    fun updateRead(@PathVariable titleHistoryId: String): ResponseEntity<BaseResp> {
        return getRespEntity(service.updateRead(titleHistoryId))
    }

    @ExplainSelectTitleHistoryUnRead
    @GetMapping("/customer/title/history/unread")
    fun findAllByUnRead(): ResponseEntity<BaseResp> {
        return getRespEntity(service.findAllForUnRead(getAuthReq()))
    }

    @ExplainSelectTitleHistory
    @GetMapping("/customer/title/history")
    fun findAllByCustomerId(): ResponseEntity<BaseResp> {
        return getRespEntity(service.findAllByCustomerId(getAuthReq()))
    }

    @ExplainSelectTitleHistoryRank
    @GetMapping("/customer/title/history/rank")
    fun findAllByRank(@RequestParam titleId: String): ResponseEntity<BaseResp> {
        return getRespEntity(service.findAllByRank(titleId))
    }

}
