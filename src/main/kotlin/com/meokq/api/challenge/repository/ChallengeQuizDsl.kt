package com.meokq.api.challenge.repository

import com.meokq.api.challenge.request.ChallengeQuizSearchDto
import com.meokq.api.challenge.response.ReadChallengeQuizResp
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest

interface ChallengeQuizDsl {
    fun getChallengeQuizList(dto: ChallengeQuizSearchDto, userId: String?, pageable: PageRequest): PageImpl<ReadChallengeQuizResp>
}