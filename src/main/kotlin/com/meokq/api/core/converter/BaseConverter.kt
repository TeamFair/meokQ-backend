package com.meokq.api.core.converter

interface BaseConverter<REQ, RES, MODEL> {
    fun modelToResponse(model: MODEL) : RES
    fun requestToModel(request : REQ) : MODEL
}