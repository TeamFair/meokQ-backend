package com.meokq.api.xp.dto.response

import com.meokq.api.xp.model.Xp
import com.meokq.api.xp.model.XpType.*;
class XpStatsResp(
    var strength_stat: Long = 0,
    var intellect_stat: Long = 0,
    var fun_stat: Long = 0,
    var charm_stat: Long = 0,
    var sociability_stat: Long = 0
) {
    constructor(xp: List<Xp>) : this() {
        xp.forEach {
            when (it.xpType) {
                STRENGTH -> strength_stat = it.xpPoint
                INTELLECT -> intellect_stat = it.xpPoint
                FUN -> fun_stat = it.xpPoint
                CHARM -> charm_stat = it.xpPoint
                SOCIABILITY -> sociability_stat = it.xpPoint
                else -> {}
            }
        }
    }

}
