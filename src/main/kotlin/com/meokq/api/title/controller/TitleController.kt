package com.meokq.api.title.controller

import com.meokq.api.auth.enums.UserType
import com.meokq.api.auth.request.AuthReq
import com.meokq.api.core.AuthDataProvider
import com.meokq.api.core.ResponseEntityCreation
import com.meokq.api.core.dto.BaseListRespV2
import com.meokq.api.core.dto.BaseResp
import com.meokq.api.title.annotations.ExplainSelectTitle
import com.meokq.api.title.service.TitleService
import com.meokq.api.user.annotaions.*
import com.meokq.api.user.request.CustomerUpdateProfileReq
import com.meokq.api.user.request.CustomerUpdateReq
import com.meokq.api.user.request.RankSearchCondition
import com.meokq.api.user.response.CustomerResp
import com.meokq.api.user.service.CustomerService
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Tag(name = "Title", description = "칭호")
@Controller
@RequestMapping("/api")
class TitleController(
    private val service: TitleService,
): ResponseEntityCreation, AuthDataProvider {

    @ExplainSelectTitle
    @GetMapping("/admin/title")
    fun findAll(): ResponseEntity<BaseResp> {
        return getRespEntity(service.findAll())
    }

}
