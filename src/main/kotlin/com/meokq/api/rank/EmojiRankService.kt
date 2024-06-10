package com.meokq.api.rank

interface EmojiRankService<T> {

    var upperRank: MutableList<T>

    var lowerRank: MutableList<T>
    fun addToLowerRank(item: T)
}
