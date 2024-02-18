package com.meokq.api.agreement.repository

import com.meokq.api.core.repository.BaseRepository
import com.meokq.api.agreement.model.Agreement

interface AgreementRepository : BaseRepository<Agreement, String> {
    //fun findAll(spec: Specification<Agreement>?, pageable: Pageable): Page<Agreement>
}