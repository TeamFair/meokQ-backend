package com.meokq.api.rank

import com.meokq.api.challenge.model.Challenge
import jakarta.validation.constraints.Size

interface EmojiRankService<T> {

    var upperRank: MutableList<T>

    var lowerRank: MutableList<T>
    fun addToRank(item: T)
    fun fetchShuffleRankToPage(pageNumber: Int, pageSize: Int): List<Challenge>
}
