package com.meokq.api.rank

import com.meokq.api.challenge.model.Challenge
import org.springframework.stereotype.Component

@Component
class ChallengeEmojiRankService(
): EmojiRankService<Challenge> {
    //TODO 추후 영속성 데이터로 변경
    override var upperRank: MutableSet<Challenge> = mutableSetOf()
    override var lowerRank: MutableSet<Challenge> = mutableSetOf()

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


    fun getPages(): Set<Challenge> {
        val page : MutableSet<Challenge> = mutableSetOf()
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

