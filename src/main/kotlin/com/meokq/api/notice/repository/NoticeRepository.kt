package com.meokq.api.notice.repository

import com.meokq.api.core.repository.BaseRepository
import com.meokq.api.notice.model.Notice


interface NoticeRepository : BaseRepository<Notice, String>{
    //fun findByTarget(target: NoticeTarget, pageable: Pageable): Page<Notice>
}