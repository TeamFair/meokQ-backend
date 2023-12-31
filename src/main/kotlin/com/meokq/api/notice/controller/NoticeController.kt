package com.meokq.api.notice.controller

import com.meokq.api.core.controller.BaseController
import com.meokq.api.core.dto.BaseListRespV2
import com.meokq.api.core.dto.BaseResp
import com.meokq.api.core.service.BaseService
import com.meokq.api.notice.annotations.ExplainDeleteNotice
import com.meokq.api.notice.annotations.ExplainSaveNotice
import com.meokq.api.notice.annotations.ExplainSelectNoticeList
import com.meokq.api.notice.model.Notice
import com.meokq.api.notice.request.NoticeReq
import com.meokq.api.notice.request.NoticeSearchDto
import com.meokq.api.notice.response.NoticeResp
import com.meokq.api.notice.service.NoticeService
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "Notice", description = "공지사항")
@RequestMapping("/api")
@RestController
class NoticeController(
    final val service: NoticeService
) : BaseController<NoticeReq, NoticeResp, Notice, String> {
    override val _service: BaseService<NoticeReq, NoticeResp, Notice, String> = service

    @ExplainSelectNoticeList
    @GetMapping(value = ["/open/notice", "/boss/notice", "/customer/notice", "/admin/notice", ])
    fun findAll(
        searchDto: NoticeSearchDto,
        @RequestParam(defaultValue = "0") page : Int,
        @RequestParam(defaultValue = "10") size : Int,
    ) : ResponseEntity<BaseListRespV2> {
        val pageable: Pageable = PageRequest.of(page, size)
        val result = service.findAll(searchDto, pageable, getAuthReq())
        return getListRespEntityV2(result)
    }

    @ExplainSaveNotice
    @PostMapping(value = ["/admin/notice", ])
    override fun save(@RequestBody @Valid request: NoticeReq): ResponseEntity<BaseResp> {
        return super.save(request)
    }

    @ExplainDeleteNotice
    @DeleteMapping(value = ["/admin/notice/{noticeId}", ])
    override fun deleteById(@PathVariable noticeId: String) : ResponseEntity<BaseResp> {
        return super.deleteById(noticeId)
    }
}