package com.meokq.api.image.service

import com.meokq.api.image.request.ImageReq
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.io.FileNotFoundException
import java.io.IOException
import java.nio.file.DirectoryStream
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

@Service
//@Profile("!dev")
class ImgLocalServiceImpl(
    @Value("\${file.upload-dir}")
    private val uploadDir: String
) : ImgStorageService {

    override fun uploadImage(fileName: String, imageReq: ImageReq) {
        try {
            val file = imageReq.file
            val directoryPath = Paths.get(uploadDir)

            // 디렉토리가 존재하지 않으면 생성
            if (!Files.exists(directoryPath)) {
                try {
                    Files.createDirectories(directoryPath)
                } catch (e: IOException) {
                    e.printStackTrace()
                    throw e
                }
            }

            // 파일 복사
            val filePath: Path = directoryPath.resolve("${fileName}.jpeg")
            Files.copy(file.inputStream, filePath)
        } catch (e: IOException) {
            // 처리 중 발생한 예외 처리
            e.printStackTrace()
            throw e
        }
    }

    override fun downloadImage(fileName: String): ByteArray {
        val directoryPath: Path = Paths.get(uploadDir)
        val fileName = "$fileName"

        // 디렉토리 내의 파일들 중 해당 파일 이름으로 시작하는 첫 번째 파일을 찾음
        val files: List<Path> = Files.list(directoryPath)
            .use { stream ->
                stream.filter { path ->
                    path.fileName.toString().startsWith(fileName)
                }.toList()
            }

        if (files.isNotEmpty()) {
            // 가장 처음으로 찾은 파일을 다운로드
            return Files.readAllBytes(files[0])
        } else {
            // 파일이 없는 경우에 대한 예외 처리 또는 기본값 설정
            throw FileNotFoundException("File not found: $fileName")
        }
    }

    override fun deleteImage(fileName: String) {
        val directoryPath: Path = Paths.get(uploadDir)
        val fileName = "$fileName"

        // 디렉토리 내의 파일들 중 해당 파일 이름으로 시작하는 파일들을 모두 삭제
        Files.newDirectoryStream(directoryPath, "$fileName*").use { stream: DirectoryStream<Path> ->
            stream.forEach { filePath: Path ->
                Files.deleteIfExists(filePath)
            }
        }
    }
}