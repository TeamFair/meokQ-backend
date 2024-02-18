package data

import com.meokq.api.user.enums.UserStatus
import com.meokq.api.user.model.Boss
import jdk.jfr.Description

object MockBoss {
    @Description("휴면된 boss")
    val BS20000001 = Boss(
        bossId = "BS20000001",
        status = UserStatus.DORMANT,
        email = "BS20000001@example.com"
    )

    @Description("탈퇴한 boss")
    val BS20000002 = Boss(
        bossId = "BS20000002",
        status = UserStatus.WITHDRAWN,
        email = "BS20000002@example.com"
    )

    @Description("활동중인 boss1")
    val BS20000003 = Boss(
        bossId = "BS20000003",
        status = UserStatus.ACTIVE,
        email = "BS20000003@example.com"
    )

    @Description("활동중인 boss2")
    val BS20000004 = Boss(
        bossId = "BS20000004",
        status = UserStatus.ACTIVE,
        email = "BS20000004@example.com"
    )

    @Description("활동중인 boss3")
    val BS20000005 = Boss(
        bossId = "BS20000005",
        status = UserStatus.ACTIVE,
        email = "BS20000005@example.com"
    )
}