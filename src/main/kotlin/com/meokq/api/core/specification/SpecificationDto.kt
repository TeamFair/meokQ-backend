package com.meokq.api.core.specification

class SpecificationDto(
    val domainName : String,
    val paramName : String = domainName
) {
    open fun isEmpty(value : Any) : Boolean{
        if (value is String) return value.isNullOrEmpty()
        return value.equals(null)
    }
}