package com.meokq.api.user.repository

import com.meokq.api.core.repository.BaseRepository
import com.meokq.api.user.model.Agreement

interface AgreementRepository : BaseRepository<Agreement, String> {
    //fun findAll(spec: Specification<Agreement>?, pageable: Pageable): Page<Agreement>
}