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

    override fun addToRank(item: Challenge) {
        if (item.likeEmojiCnt >= RANK_THRESHOLD) {
            // 어퍼랭크에 추가, 로우랭크에서 제거
            if (upperRank.contains(item)) return
            upperRank.add(item)
            lowerRank.remove(item)
        } else {
            // 로우랭크에 추가, 어퍼랭크에서 제거
            if (lowerRank.contains(item)) return
            lowerRank.add(item)
            upperRank.remove(item)
        }
    }


    fun getPages(requestPageNumber: Int, pageSize: Int): List<Challenge> {
        val page = mutableListOf<Challenge>()
        val halfPageSize = pageSize / 2
        val upperStartIndex = requestPageNumber * halfPageSize
        val lowerStartIndex = requestPageNumber * halfPageSize

        val upperPart = upperRank.drop(upperStartIndex).take(halfPageSize)
        val lowerPart = lowerRank.drop(lowerStartIndex).take(halfPageSize).toMutableList()

        if (upperPart.size < halfPageSize) {
            val remainingSize = pageSize - upperPart.size
            val lastIndex = lowerRank.indexOf(lowerPart.lastOrNull() ?: lowerRank.first())
            val remainingLowerPart = lowerRank.drop(lastIndex + 1).take(remainingSize)
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

