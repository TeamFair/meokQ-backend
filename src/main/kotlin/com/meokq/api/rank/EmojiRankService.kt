package com.meokq.api.rank

import com.meokq.api.challenge.model.Challenge

interface EmojiRankService<T> {

    var upperRank: MutableList<T>

    var lowerRank: MutableList<T>
    fun addToRank(item: T)
    fun fetchShuffleRankToPage(): Set<Challenge>
}
