package com.meokq.api.core

import com.meokq.api.core.dto.BaseListRespV2
import com.meokq.api.core.dto.BaseResp
import com.meokq.api.core.enums.ErrorStatus
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import java.lang.Integer.min
import java.util.Collections.emptyList

interface ResponseEntityCreation {
    fun getListRespEntity(page: Page<*>): ResponseEntity<BaseListRespV2> {
        return ResponseEntity.ok(
            BaseListRespV2(
                content = page.content.toMutableList(),
                totalElements = page.totalElements,
                size = page.size,
                number = page.number
            )
        )
    }

    fun getListRespEntity(list: List<*>): ResponseEntity<BaseListRespV2> {
        val page = listToPage(list, 0, list.size)
        return getListRespEntity(page)
    }

    fun <T> listToPage(list: List<T>, page: Int, size: Int): Page<T> {
        val start = page * size
        val end = min(start + size, list.size)

        if (start >= list.size) {
            return PageImpl(emptyList(), PageRequest.of(page, size), list.size.toLong())
        }

        val subList = list.subList(start, end)
        return PageImpl(subList, PageRequest.of(page, size), list.size.toLong())
    }

    fun getRespEntity(resp : Any?, errorStatus : ErrorStatus = ErrorStatus.OK): ResponseEntity<BaseResp> {
        return ResponseEntity.ok(BaseResp(resp))
    }
}