package com.meokq.api.application.controller

import com.meokq.api.application.request.QuestRequest
import com.meokq.api.application.response.QuestResponse
import com.meokq.api.application.service.QuestService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/quests")
class QuestController {

    @Autowired
    lateinit var service : QuestService

    @PostMapping
    fun save(@RequestBody request : QuestRequest) : ResponseEntity<QuestResponse> {
        val saveData = service.save(request)
        return ResponseEntity
            .created(URI.create("/missions/${saveData.questId}"))
            .body(saveData)
    }
}