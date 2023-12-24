package com.meokq.api.notice.controller

import com.meokq.api.core.controller.BaseController
import com.meokq.api.core.dto.BaseListRespV2
import com.meokq.api.core.dto.BaseResp
import com.meokq.api.core.service.BaseService
import com.meokq.api.notice.model.Notice
import com.meokq.api.notice.request.NoticeReq
import com.meokq.api.notice.request.NoticeSearchDto
import com.meokq.api.notice.response.NoticeResp
import com.meokq.api.notice.service.NoticeService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "Notice", description = "공지사항")
@RestController
@RequestMapping("/api")
class NoticeController(
    final val service: NoticeService
) : BaseController<NoticeReq, NoticeResp, Notice, String> {
    override val _service: BaseService<NoticeReq, NoticeResp, Notice, String> = service

    @Operation(
        summary = "(INT001) 공지사항 조회",
        description = """
            사용자의 타입에 따라 조회할 수 있는 유형이 정해집니다.
            UserType.BOSS -> NoticeTarget.BOSS
            UserType.CUSTOMER -> NoticeTarget.CUSTOMER
            UserType.UNKNOWN -> NoticeTarget.ALL
        """,
        parameters = [
            Parameter(name = "page", description = "페이지 번호", required = false),
            Parameter(name = "size", description = "페이지 크기", required = false)
        ]
    )

    @ApiResponse(
        responseCode = "200",
        description = "성공",
        content = [Content(
            mediaType = "application/json",
            //schema = Schema(implementation = BaseResp::class),
            examples = [ExampleObject(value = """
{
  "size": 10,
  "data": [
    {
      "noticeId": "NT00000004",
      "title": "General Notice",
      "content": "Content for everyone",
      "createDate": null,
      "target": "ALL"
    }
  ],
  "total": 1,
  "page": 0,
  "status": "OK",
  "message": "Your request has been processed successfully."
}
                """)])]
    )
    @GetMapping(value = [
        "/open/notice",
        "/boss/notice",
        "/customer/notice",
        "/admin/notice",
    ])
    fun findAll(
        searchDto: NoticeSearchDto,
        @RequestParam(defaultValue = "0") page : Int,
        @RequestParam(defaultValue = "10") size : Int,
    ) : ResponseEntity<BaseListRespV2> {
        val pageable: Pageable = PageRequest.of(page, size)
        val result = service.findAll(searchDto, pageable, getAuthReq())
        return getListRespEntityV2(result)
    }

    @Operation(
        summary = "(INT002) 공지사항 등록",
        description = "공지사항을 등록합니다."
    )
    @ApiResponse(
        responseCode = "200",
        description = "성공",
        content = [Content(
            mediaType = "application/json",
            //schema = Schema(implementation = BaseResp::class),
            examples = [ExampleObject(value = """
{
  "data": {
    "noticeId": "35bc8ab7-d55a-4f97-b174-bfcbdda29365",
    "title": "공지사항 제목",
    "content": "공지사항 테스트입니다.",
    "createDate": "2023-12-25 02:14:24",
    "target": "ALL"
  },
  "status": "OK",
  "message": "Your request has been processed successfully."
}
                """)])]
    )
    @PostMapping(value = [
        "/admin/notice",
    ])
    override fun save(@RequestBody @Valid request: NoticeReq): ResponseEntity<BaseResp> {
        return super.save(request)
    }

    @Operation(
        summary = "(INT003) notice 정보 삭제",
        description = "notice 정보를 삭제합니다.",
        parameters = [
            Parameter(name = "noticeId", description = "notice 아이디", required = true, example = "NT00000001"),
        ]
    )
    @ApiResponse(
        responseCode = "200",
        description = "성공",
        content = [Content(
            mediaType = "application/json",
            //schema = Schema(implementation = BaseResp::class),
            examples = [ExampleObject(value = """
                """)])]
    )
    @DeleteMapping(value = [
        "/admin/notice/{noticeId}",
    ])
    override fun deleteById(@PathVariable noticeId: String) : ResponseEntity<BaseResp> {
        return super.deleteById(noticeId)
    }
}