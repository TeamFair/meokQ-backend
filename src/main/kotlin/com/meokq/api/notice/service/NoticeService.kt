package com.meokq.api.notice.service

import com.meokq.api.auth.enums.UserType
import com.meokq.api.auth.request.AuthReq
import com.meokq.api.core.converter.BaseConverter
import com.meokq.api.core.service.BaseService
import com.meokq.api.notice.converter.NoticeConverter
import com.meokq.api.notice.enums.NoticeTarget
import com.meokq.api.notice.model.Notice
import com.meokq.api.notice.repository.NoticeRepository
import com.meokq.api.notice.request.NoticeReq
import com.meokq.api.notice.request.NoticeSearchDto
import com.meokq.api.notice.response.NoticeResp
import com.meokq.api.notice.specification.NoticeSpecification
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

    fun findAll(
        searchDto: NoticeSearchDto,
        pageable: Pageable,
        authReq: AuthReq,
    ): Page<NoticeResp> {
         searchDto.target = when(authReq.userType){
            UserType.BOSS -> NoticeTarget.BOSS
            UserType.CUSTOMER -> NoticeTarget.CUSTOMER
            UserType.UNKNOWN -> NoticeTarget.ALL
            else -> searchDto.target
        }

        val specification = NoticeSpecification.bySearchDto(searchDto)
        val page = repository.findAll(specification, pageable)
        val content = page.content.map { NoticeResp(it) }
        val count = repository.count(specification)
        return PageImpl(content, pageable, count)
    }
}