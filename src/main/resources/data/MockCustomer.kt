package data

import com.meokq.api.user.enums.UserStatus
import com.meokq.api.user.model.Customer
import jdk.jfr.Description

object MockCustomer {
    @Description("휴면된 customer")
    val CS20000001 = Customer(
        customerId = "CS20000001",
        status = UserStatus.DORMANT,
        email = "CS20000001@example.com",
        nickname = "CS20000001"
    )

    @Description("탈퇴한 customer")
    val CS20000002 = Customer(
        customerId = "CS20000002",
        status = UserStatus.WITHDRAWN,
        email = "CS20000002@example.com",
        nickname = "CS20000002"
    )

    @Description("활동중인 customer1")
    val CS20000003 = Customer(
        customerId = "CS20000003",
        status = UserStatus.ACTIVE,
        email = "CS20000003@example.com",
        nickname = "CS20000003"
    )

    @Description("활동중인 customer2")
    val CS20000004 = Customer(
        customerId = "CS20000004",
        status = UserStatus.ACTIVE,
        email = "CS20000004@example.com",
        nickname = "CS20000004"
    )

    @Description("활동중인 customer3")
    val CS20000005 = Customer(
        customerId = "CS20000005",
        status = UserStatus.ACTIVE,
        email = "CS20000005@example.com",
        nickname = "CS20000005"
    )
}