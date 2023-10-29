package com.meokq.api.application.response

import lombok.Data
import org.springframework.data.domain.Page

@Data
class BaseListResponse<T>(
    pageResult : Page<T>,
){
    val data: MutableList<T> = pageResult.content
    val total : Long = pageResult.totalElements   // 전체 항목 수
    val size : Int = pageResult.size              // 페이지당 항목 수
    val page : Int = pageResult.number            // 현재 페이지
}