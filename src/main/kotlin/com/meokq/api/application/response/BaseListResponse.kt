package com.meokq.api.application.response

import com.meokq.api.application.enums.ErrorStatus
import org.springframework.data.domain.Page

class BaseListResponse<T>(
    pageResult: Page<T>,
    errorStatus: ErrorStatus = ErrorStatus.OK
){
    val data: MutableList<T> = pageResult.content
    val total : Long = pageResult.totalElements   // 전체 항목 수
    val size : Int = pageResult.size              // 페이지당 항목 수
    val page : Int = pageResult.number            // 현재 페이지
    var status : String? = errorStatus.name
    var message : String? = errorStatus.message
}