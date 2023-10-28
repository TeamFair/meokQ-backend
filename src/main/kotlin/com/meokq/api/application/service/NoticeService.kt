package com.meokq.api.application.service

import com.meokq.api.application.enums.UserType
import com.meokq.api.application.model.Notice
import com.meokq.api.application.repository.NoticeRepository
import com.meokq.api.application.request.NoticeRequest
import com.meokq.api.application.request.NoticeSearchDto
import com.meokq.api.application.response.NoticeResponse
import com.meokq.api.core.converter.NoticeConverter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class NoticeService {

    @Autowired
    lateinit var repository : NoticeRepository

    @Autowired
    lateinit var converter : NoticeConverter

    fun findAll(searchDto: NoticeSearchDto): Page<NoticeResponse> {
        val pageable = searchDto.pageable
        val page = repository.findByTarget(searchDto.target, pageable)
        val content = page.content.map { converter.modelToDto(it) }
        return PageImpl(content, pageable, page.totalElements)
    }

    fun save(request : NoticeRequest) : Notice {
        val notice = converter.dtoToModel(request)
        return repository.save(notice)
    }
}