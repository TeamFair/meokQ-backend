package com.meokq.api.rank

import com.meokq.api.challenge.model.Challenge
import org.springframework.stereotype.Component

@Component
class ChallengeEmojiRankService(
): EmojiRankService<Challenge> {
    override var upperRank: MutableList<Challenge> = mutableListOf()
    override var lowerRank: MutableList<Challenge> = mutableListOf()

    override fun addToLowerRank(item: Challenge) {
        if (validLowerRank(item)){
            lowerRank.add(item)
        }else{
            addToUpperRank(item)
        }
    }

    private fun validLowerRank(item: Challenge): Boolean {
        return !lowerRank.contains(item) && item.likeEmojiCnt < 5
    }

    private fun addToUpperRank(item: Challenge) {
        if (lowerRank.remove(item)) {
            upperRank.add(item)
        }
    }


}