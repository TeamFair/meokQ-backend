package com.meokq.api.application.service

import com.meokq.api.application.model.Notice
import com.meokq.api.application.repository.NoticeRepository
import com.meokq.api.application.request.NoticeRequest
import com.meokq.api.application.response.NoticeResponse
import com.meokq.api.core.converter.NoticeConverter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class NoticeService {

    @Autowired
    lateinit var repository : NoticeRepository

    @Autowired
    lateinit var converter : NoticeConverter

    fun findAll(): List<NoticeResponse> {
        val modelList = repository.findAll()
        return modelList.stream().map { converter.modelToDto(it) }.toList()
    }

    fun save(request : NoticeRequest) : Notice {
        val notice = converter.dtoToModel(request)
        return repository.save(notice)
    }
}