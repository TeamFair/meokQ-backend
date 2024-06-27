package com.meokq.api.rank

interface EmojiRankService<T> {

    var upperRank: MutableSet<T>

    var lowerRank: MutableSet<T>
    fun addToRank(item: T)
}
