package com.meokq.api.market.annotations

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.responses.ApiResponse

@Operation(
    summary = "(IMK008) 마켓 인증정보 조회",
    description = "마켓 인증정보를 조회합니다.",
    //tags = ["Market"],
    parameters = [
        Parameter(name = "page", description = "페이지 번호", required = false),
        Parameter(name = "size", description = "페이지 크기", required = false)
    ]
)
@ApiResponse(
    responseCode = "200",
    description = "성공",
    content = [Content(
        mediaType = "application/json",
        //schema = Schema(implementation = BaseResp::class),
        examples = [ExampleObject(value = """
{
  "size": 2,
  "data": [
    {
      "recordId": "MA00000003",
      "marketId": "MK00000003",
      "reviewResult": null,
      "comment": null,
      "operator": {
        "name": "Bob Johnson",
        "birthdate": "1978-09-30",
        "idcardImage": {
          "imageId": "IM10000002",
          "location": "/path/to/image2"
        }
      },
      "license": {
        "licenseId": "LICENSE789",
        "licenseImage": {
          "imageId": "IM10000001",
          "location": "/path/to/image1"
        },
        "ownerName": "Bob Johnson",
        "marketName": "Johnson Market",
        "address": "789 Elm St",
        "postalCode": "34567"
      }
    },
    {
      "recordId": "MA00000002",
      "marketId": "MK00000002",
      "reviewResult": "REJECTED",
      "comment": "Not suitable",
      "operator": {
        "name": "Jane Smith",
        "birthdate": "1985-05-15",
        "idcardImage": {
          "imageId": "IM10000002",
          "location": "/path/to/image2"
        }
      },
      "license": {
        "licenseId": "LICENSE456",
        "licenseImage": {
          "imageId": "IM10000001",
          "location": "/path/to/image1"
        },
        "ownerName": "Jane Smith",
        "marketName": "Smith Market",
        "address": "456 Oak St",
        "postalCode": "67890"
      }
    }
  ],
  "total": 2,
  "page": 0,
  "status": "OK",
  "message": "Your request has been processed successfully."
}
                """)])]
)
annotation class ExplainSelectMarketAuthList()
