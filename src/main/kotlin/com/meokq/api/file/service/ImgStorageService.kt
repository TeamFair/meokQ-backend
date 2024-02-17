package com.meokq.api.file.service

import com.meokq.api.file.request.ImageReq

interface ImgStorageService {
    fun uploadImage(fileName: String, imageReq: ImageReq)
    fun downloadImage(fileName: String): ByteArray
    fun deleteImage(fileName: String)
}