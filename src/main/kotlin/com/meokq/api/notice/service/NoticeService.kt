package com.meokq.api.notice.service

import com.meokq.api.auth.enums.UserType
import com.meokq.api.auth.request.AuthReq
import com.meokq.api.core.JpaService
import com.meokq.api.core.JpaSpecificationService
import com.meokq.api.core.dto.BaseResp
import com.meokq.api.core.repository.BaseRepository
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
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class NoticeService(
    private val repository : NoticeRepository,
) :JpaService<Notice, String>, JpaSpecificationService<Notice, String> {
    override var jpaRepository: JpaRepository<Notice, String> = repository
    override val jpaSpecRepository: BaseRepository<Notice, String> = repository
    private val specifications = NoticeSpecification

    fun registerNotice(request: NoticeReq): NoticeResp {
        val notice = saveModel(Notice(request))
        return NoticeResp(notice)
    }
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

        val specification = specifications.bySearchDto(searchDto)
        val models = findAllBy(specification, pageable)
        val responses = models.map { NoticeResp(it) }
        val count = repository.count(specification)
        return PageImpl(responses, pageable, count)
    }
}