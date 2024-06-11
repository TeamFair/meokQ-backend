package com.meokq.api.rank

import com.meokq.api.challenge.model.Challenge
import org.springframework.stereotype.Component

@Component
class ChallengeEmojiRankService(
): EmojiRankService<Challenge> {
    //TODO 추후 영속성 데이터로 변경
    override var upperRank: MutableList<Challenge> = mutableListOf()
    override var lowerRank: MutableList<Challenge> = mutableListOf()

    //한 페이지당 표시 될 아이템 수
    private val PAGE_SIZE = 10

    //랭크 기준 값
    private val RANK_THRESHOLD = 5

    override fun addToLowerRank(item: Challenge) {
        if (validLowerRank(item)){
            lowerRank.add(item)
        }else{
            addToUpperRank(item)
        }
    }

    private fun validLowerRank(item: Challenge): Boolean {
        return !lowerRank.contains(item) && item.likeEmojiCnt < RANK_THRESHOLD
    }

    private fun addToUpperRank(item: Challenge) {
        if (lowerRank.remove(item)) {
            upperRank.add(item)
        }
    }

    fun getPages(): List<Challenge> {
        val page = mutableListOf<Challenge>()
        val upperPart = upperRank.take(PAGE_SIZE/2)
        val lowerPart = lowerRank.take(PAGE_SIZE/2).toMutableList()

        // 페이지가 10개가 안되면 남은 부분을 하위랭크에서 추가
        if (upperPart.size < 5) {
            val lastIndex = lowerRank.indexOf(lowerPart.last())
            val remainingLowerPart = lowerRank.drop(lastIndex + 1).take(PAGE_SIZE - page.size)
            lowerPart.addAll(remainingLowerPart)
        }
        val maxLength = maxOf(upperPart.size, lowerPart.size)

        for (i in 0 until maxLength) {
            if (i < upperPart.size) {
                page.add(upperPart[i])
            }
            if (i < lowerPart.size) {
                page.add(lowerPart[i])
            }
        }
        return page
    }

}

