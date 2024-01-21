package com.meokq.api.image.service

import com.meokq.api.image.request.ImageReq

interface ImgStorageService {
    fun uploadImage(fileName: String, imageReq: ImageReq)
    fun downloadImage(fileName: String): ByteArray
    fun deleteImage(fileName: String)
}