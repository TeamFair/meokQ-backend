package com.meokq.api.rank

import com.meokq.api.challenge.model.Challenge
import org.springframework.stereotype.Component

@Component
class ChallengeEmojiRankService: EmojiRankService<Challenge> {
    override var upperRank: MutableList<Challenge> = mutableListOf()
    override var lowerRank: MutableList<Challenge> = mutableListOf()

    override fun addToUpperRank(item: Challenge) {
        if (lowerRank.remove(item)) {
            upperRank.add(item)
        }
    }

    override fun addToLowerRank(item: Challenge) {
        if (validRank(item)){
            lowerRank.add(item)
        }
    }

    private fun validRank(item: Challenge): Boolean {
        return !upperRank.contains(item) && !lowerRank.contains(item)
    }

}