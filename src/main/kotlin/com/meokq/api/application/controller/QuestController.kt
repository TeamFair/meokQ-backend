package com.meokq.api.application.controller

import com.meokq.api.application.request.QuestRequest
import com.meokq.api.application.response.BaseListResponse
import com.meokq.api.application.response.QuestResponse
import com.meokq.api.application.service.QuestService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/quests")
class QuestController {

    @Autowired
    lateinit var service : QuestService

    @GetMapping
    fun findAll(
        @RequestParam marketId : String,
        @RequestParam(defaultValue = "0") page : Int,
        @RequestParam(defaultValue = "10") size : Int,
    ) : ResponseEntity<BaseListResponse<QuestResponse>> {
        val pageable: Pageable = PageRequest.of(page, size)
        val result = service.findAllByMarketId(marketId, pageable)
        return ResponseEntity.ok(BaseListResponse(
            content = result.content,
            totalElements = result.totalElements,
            size = result.size,
            number = result.number
        ))
    }

    @PostMapping
    fun save(@RequestBody request : QuestRequest) : ResponseEntity<QuestResponse> {
        val saveData = service.save(request)
        return ResponseEntity
            .created(URI.create("/missions/${saveData.questId}"))
            .body(saveData)
    }
}