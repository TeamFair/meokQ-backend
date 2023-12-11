package com.meokq.api.notice.controller

import com.meokq.api.core.controller.BaseController
import com.meokq.api.core.dto.BaseListResp
import com.meokq.api.core.dto.BaseResp
import com.meokq.api.core.service.BaseService
import com.meokq.api.notice.enums.NoticeTarget
import com.meokq.api.notice.model.Notice
import com.meokq.api.notice.request.NoticeReq
import com.meokq.api.notice.response.NoticeResp
import com.meokq.api.notice.service.NoticeService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "Notice", description = "공지사항")
@RestController
@RequestMapping("/api/notices")
class NoticeController(
    final val service: NoticeService
) : BaseController<NoticeReq, NoticeResp, Notice, String> {
    override val _service: BaseService<NoticeReq, NoticeResp, Notice, String> = service

    @Operation(
        summary = "공지사항 조회",
        description = "공지사항을 조회합니다.",
        parameters = [
            Parameter(name = "target", description = "사용자 유형", required = true),
            Parameter(name = "page", description = "페이지 번호", required = false),
            Parameter(name = "size", description = "페이지 크기", required = false)
        ]
    )
    @GetMapping
    fun findAll(
        @RequestParam target : NoticeTarget,
        @RequestParam(defaultValue = "0") page : Int,
        @RequestParam(defaultValue = "10") size : Int,
    ) : ResponseEntity<BaseListResp<NoticeResp>> {
        val pageable: Pageable = PageRequest.of(page, size)
        val result = service.findAll(target, pageable)
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
        summary = "공지사항 등록",
        description = "공지사항을 등록합니다."
    )
    @PostMapping
    override fun save(@Valid request: NoticeReq): ResponseEntity<BaseResp> {
        return super.save(request)
    }

    @Operation(
        summary = "notice 정보 삭제",
        description = "notice 정보를 삭제합니다.",
        parameters = [
            Parameter(name = "noticeId", description = "notice 아이디", required = true),
        ]
    )
    @DeleteMapping("/{noticeId}")
    override fun deleteById(@PathVariable noticeId: String) : ResponseEntity<BaseResp> {
        return super.deleteById(noticeId)
    }
}