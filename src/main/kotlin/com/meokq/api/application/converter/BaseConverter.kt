package com.meokq.api.application.converter

interface BaseConverter<REQ, RES, MODEL> {
    fun modelToResponse(model: MODEL) : RES
    fun requestToModel(request : REQ) : MODEL

    fun modelToResponse(model: List<MODEL>): List<RES> {
        return model.map { modelToResponse(it) }
    }

    fun requestToModel(requests: List<REQ>): List<MODEL> {
        return requests.map { requestToModel(it) }
    }
}