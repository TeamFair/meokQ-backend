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

    override fun fetchShuffleRankToPage(pageNumber: Int, pageSize: Int): List<Challenge> {
        val result = mutableListOf<Challenge>()
        val halfPageSize = pageSize / 2
        val upperRankSize = upperRank.size - pageNumber * halfPageSize // 0 5 10
        val lowerRankSize = lowerRank.size - pageNumber * halfPageSize // 0 5 10
        val upperPart : List<Challenge>
        val lowerPart : List<Challenge>

        fun getListPart(list: List<Challenge>, start: Int, size: Int): List<Challenge> {
            return if (start >= list.size) emptyList() else list.drop(start).take(size)
        }

        val startIndex = pageNumber * halfPageSize
        when {
            upperRankSize > 5 && lowerRankSize > 5 -> {
                upperPart = getListPart(upperRank, startIndex, halfPageSize)
                lowerPart = getListPart(lowerRank, startIndex, halfPageSize)
            }
            upperRankSize <= 0 && lowerRankSize > 0 -> {
                // 상위 데이터가 부족할 때 하위 데이터만 추출
                val preEndIndex =  halfPageSize - upperRank.size % 10

                upperPart = emptyList()
                lowerPart = getListPart(lowerRank, startIndex + preEndIndex, pageSize)
            }
            upperRankSize > 0 && lowerRankSize <= 0 -> {
                // 하위 데이터가 부족할 때 상위 데이터만 추출
                val preEndIndex =  halfPageSize - lowerRank.size % 10

                upperPart = getListPart(upperRank,startIndex + preEndIndex, pageSize)
                lowerPart = emptyList()
            }
            upperRankSize in 1..4 || lowerRankSize in 1..4 -> {
                if (upperRankSize > lowerRankSize){

                    lowerPart = getListPart(lowerRank, startIndex, pageSize)
                    val upperPageSize = pageSize - lowerPart.size
                    upperPart = getListPart(upperRank, startIndex, upperPageSize)

                }else{
                    upperPart = getListPart(upperRank, startIndex, pageSize)
                    val lowerPageSize = pageSize - upperPart.size
                    lowerPart = getListPart(lowerRank, startIndex, lowerPageSize)

                }
            }
            else -> {
                // 예기치 않은 상태
                throw IllegalStateException("Unexpected state with upperRankAvailable = $upperRank and lowerRankAvailable = $lowerRank")
            }
        }

        for (i in 0 until pageSize) {
            if (i < upperPart.size) {
                result.add(upperPart[i])
            }
            if (i < lowerPart.size) {
                result.add(lowerPart[i])
            }
        }
        return result
    }

}

