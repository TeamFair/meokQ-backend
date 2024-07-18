package com.meokq.api.rank

import com.meokq.api.challenge.model.Challenge

interface EmojiRankService<T> {

    var upperRank: MutableSet<T>

    var lowerRank: MutableSet<T>
    fun addToRank(item: T)
    fun fetchShuffleRankToPage(): Set<Challenge>
}
