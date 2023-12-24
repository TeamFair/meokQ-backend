package com.meokq.api.core.enums

enum class SeqGenerator(
    val sequence : Sequence,
    val prefix: Prefix,
) {
    CHALLENGE_ID(
        sequence = Sequence.SEQ_CHALLENGE_ID,
        prefix = Prefix.CH,
    ),

    COUPON_ID(
        sequence = Sequence.SEQ_COUPON_ID,
        prefix = Prefix.CP,
    ),

    IMAGE_ID(
        sequence = Sequence.SEQ_IMAGE_ID,
        prefix = Prefix.IM
    ),
    ;

    companion object {
        fun fromString(value: String): SeqGenerator {
            return requireNotNull(SeqGenerator.values().find { it.name.equals(value, ignoreCase = true) }) {
                "No enum constant SeqGenerator for string: $value"
            }
        }
    }
}