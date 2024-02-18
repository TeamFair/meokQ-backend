package data

import com.meokq.api.market.model.LicenseAuth
import data.MockImage.IM10000001
import jdk.jfr.Description

object MockLicenseAuth {
    @Description("영업신고증1")
    val BR20000001 = LicenseAuth(
        recordId = "BR20000001",
        licenseId = "LICENSE001",
        ownerName = "John Doe",
        marketName = "Doe Market",
        address = "123 Main St",
        postalCode = "12345",
        licenseImage = IM10000001
    )

    @Description("영업신고증2")
    val BR20000002 = LicenseAuth(
        recordId = "BR20000002",
        licenseId = "LICENSE002",
        ownerName = "Jane Smith",
        marketName = "Smith Market",
        address = "456 Oak St",
        postalCode = "67890",
        licenseImage = IM10000001
    )

    @Description("영업신고증3")
    val BR20000003 = LicenseAuth(
        recordId = "BR20000003",
        licenseId = "LICENSE003",
        ownerName = "Bob Johnson",
        marketName = "Johnson Market",
        address = "789 Elm St",
        postalCode = "34567",
        licenseImage = IM10000001
    )
}