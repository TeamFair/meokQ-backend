package data

import com.meokq.api.market.model.OperatorAuth
import data.MockImage.IM10000002
import jdk.jfr.Description
import java.time.LocalDate

object MockOperatorAuth {
    @Description("사업자개인정보1")
    val PR20000001 = OperatorAuth(
        recordId = "PR20000001",
        ownerName = "John Doe",
        ownerBirthdate = LocalDate.parse("19900101"),
        idcardImage = IM10000002
    )

    @Description("사업자개인정보2")
    val PR20000002 = OperatorAuth(
        recordId = "PR20000002",
        ownerName = "Jane Smith",
        ownerBirthdate = LocalDate.parse("19600101"),
        idcardImage = IM10000002
    )

    @Description("사업자개인정보3")
    val PR20000003 = OperatorAuth(
        recordId = "PR20000003",
        ownerName = "Bob Johnson",
        ownerBirthdate = LocalDate.parse("19780930"),
        idcardImage = IM10000002
    )
}