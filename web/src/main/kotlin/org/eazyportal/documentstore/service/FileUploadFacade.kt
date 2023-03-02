package org.eazyportal.documentstore.service

import org.eazyportal.documentstore.service.upload.FileHandler
import org.eazyportal.documentstore.web.rest.controller.DocumentUploadRequest
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class FileUploadFacade(private val fileHandler: FileHandler) {

    private val logger = LoggerFactory.getLogger(FileUploadFacade::class.java)

    fun uploadFile(file: MultipartFile, document: DocumentUploadRequest) {
        logger.info("Start uploading file: ${file.originalFilename}")
        val savedFileName = fileHandler.handle(document.memberId, document.documentType, file)
        logger.info("File has been saved as: $savedFileName")
    }
}
