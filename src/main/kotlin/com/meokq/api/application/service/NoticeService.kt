package com.meokq.api.application.service

import com.meokq.api.application.enums.UserType
import com.meokq.api.application.model.Notice
import com.meokq.api.application.repository.NoticeRepository
import com.meokq.api.application.request.NoticeReq
import com.meokq.api.application.response.NoticeResp
import com.meokq.api.application.converter.BaseConverter
import com.meokq.api.application.converter.NoticeConverter
import com.meokq.api.core.service.BaseService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service

@Service
class NoticeService(
    final val repository : NoticeRepository,
    final val converter : NoticeConverter
) : BaseService<NoticeReq, NoticeResp, Notice, String> {
    override var _converter: BaseConverter<NoticeReq, NoticeResp, Notice> = converter
    override var _repository: JpaRepository<Notice, String> = repository

    fun findAll(target: UserType, pageable: Pageable): Page<NoticeResp> {
        val page = repository.findByTarget(target, pageable)
        val content = page.content.map { converter.modelToResponse(it) }
        return PageImpl(content, pageable, page.totalElements)
    }
}