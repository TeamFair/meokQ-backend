package com.meokq.api.application.service

import com.meokq.api.application.enums.UserType
import com.meokq.api.application.model.Notice
import com.meokq.api.application.repository.NoticeRepository
import com.meokq.api.application.request.NoticeRequest
import com.meokq.api.application.response.NoticeResponse
import com.meokq.api.core.converter.BaseConverter
import com.meokq.api.core.converter.NoticeConverter
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service

@Service
class NoticeService(
    final val repository : NoticeRepository,
    final val converter : NoticeConverter
) : BaseService<NoticeRequest, NoticeResponse, Notice, String> {
    override var _converter: BaseConverter<NoticeRequest, NoticeResponse, Notice> = converter
    override var _repository: JpaRepository<Notice, String> = repository

    fun findAll(target: UserType, pageable: Pageable): Page<NoticeResponse> {
        val page = repository.findByTarget(target, pageable)
        val content = page.content.map { converter.modelToResponse(it) }
        return PageImpl(content, pageable, page.totalElements)
    }
}