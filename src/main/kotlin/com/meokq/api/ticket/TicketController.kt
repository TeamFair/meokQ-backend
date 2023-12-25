package com.meokq.api.ticket

import com.meokq.api.core.dto.BaseResp
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
@Tag(name = "Ticket", description = "이용권(퀘스트 게시권)")
class TicketController(
    private val service: TicketService
) {

    @ExplainTicketBuy
    @PostMapping(value = ["/boss/ticket"])
    fun buyTicket(
        //@Valid @RequestBody request : TicketPmtReq,
    ) : ResponseEntity<BaseResp> {
        //return ResponseEntity.ok(BaseResp(service(request)))
        return ResponseEntity.ok(BaseResp(null))
    }

    @ExplainApproveTicketPmt
    @PostMapping(value = ["/admin/ticket/approve"])
    fun approve(){}

    @ExplainSelectTicketHis
    @GetMapping(value = ["/admin/ticket", "/boss/ticket"])
    fun findAll(){}
}