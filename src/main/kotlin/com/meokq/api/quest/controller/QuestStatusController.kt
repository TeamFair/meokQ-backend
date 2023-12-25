package com.meokq.api.quest.controller

import com.meokq.api.core.dto.BaseResp
import com.meokq.api.core.enums.ErrorStatus
import com.meokq.api.quest.annotations.ExplainUpdateQuestDelete
import com.meokq.api.quest.annotations.ExplainUpdateQuestPublish
import com.meokq.api.quest.request.QuestDeleteReq
import com.meokq.api.quest.request.QuestPublishReq
import com.meokq.api.quest.response.QuestUpdateResp
import com.meokq.api.quest.service.QuestStatusService
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class QuestStatusController(
    private val service: QuestStatusService
) {
    /**
     * 퀘스트 상태변경
     */
    @PutMapping("/boss/quest/publish")
    @ExplainUpdateQuestPublish
    @Transactional(rollbackFor = [Exception::class])
    fun publishQuest(req : QuestPublishReq) : ResponseEntity<BaseResp> {
        return getRespEntity(QuestUpdateResp(service.publishQuest(req)))
    }

    @PutMapping("/boss/quest/delete")
    @ExplainUpdateQuestDelete
    @Transactional(rollbackFor = [Exception::class])
    fun deleteQuest(req : QuestDeleteReq) : ResponseEntity<BaseResp> {
        return getRespEntity(QuestUpdateResp(service.deleteQuest(req)))
    }

    private fun getRespEntity(resp : Any?, errorStatus : ErrorStatus = ErrorStatus.OK): ResponseEntity<BaseResp> {
        return ResponseEntity.ok(BaseResp(resp))
    }
}