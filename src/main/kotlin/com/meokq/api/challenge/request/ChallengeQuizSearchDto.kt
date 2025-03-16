package com.meokq.api.challenge.request

import com.meokq.api.challenge.enums.ChallengeStatus
import com.meokq.api.quest.enums.QuestType
import lombok.Getter

/**
 * 퀴즈 타입의 챌린지 검색 DTO
 */
@Getter
class ChallengeQuizSearchDto (
    val questId: String? = null,
    val status: ChallengeStatus? = null,
    val questType: QuestType? = null,
) {

}