package com.meokq.api.user.controller

import com.meokq.api.core.AuthDataProvider
import com.meokq.api.core.ResponseEntityCreation
import com.meokq.api.core.dto.BaseResp
import com.meokq.api.user.annotaions.ExplainGetRankForXpCustomer
import com.meokq.api.user.annotaions.ExplainSelectCustomer
import com.meokq.api.user.annotaions.ExplainUpdateCustomer
import com.meokq.api.user.request.CustomerUpdateReq
import com.meokq.api.user.request.RankSearchCondition
import com.meokq.api.user.service.CustomerService
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@Tag(name = "User", description = "사용자 정보")
@Controller
@RequestMapping("/api")
class CustomerController(
    private val service: CustomerService
): ResponseEntityCreation, AuthDataProvider {
    @ExplainSelectCustomer
    @GetMapping("/customer/user")
    fun findById(): ResponseEntity<BaseResp> {
        return getRespEntity(
            service.findByAuthReq(getAuthReq())
        )
    }

    @ExplainUpdateCustomer
    @PutMapping("/customer/user")
    fun update(@Valid @RequestBody request : CustomerUpdateReq): ResponseEntity<BaseResp> {
        return getRespEntity(service.update(
            authReq = getAuthReq(),
            request = request
        ))
    }

    @ExplainGetRankForXpCustomer
    @GetMapping("/customer/rank")
    fun getRankForXp(request : RankSearchCondition): ResponseEntity<BaseResp> {
        return getRespEntity(service.getRankForXp(
            request
        ))
    }
}