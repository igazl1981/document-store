package org.eazyportal.documentstore.web.service

import org.eazyportal.documentstore.service.document.DocumentService
import org.eazyportal.documentstore.service.upload.FileHandler
import org.eazyportal.documentstore.web.rest.model.DocumentUploadRequest
import org.eazyportal.documentstore.web.service.transformer.DocumentTransformer
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class FileUploadFacade(
    private val fileHandler: FileHandler,
    private val documentTransformer: DocumentTransformer,
    private val documentService: DocumentService
) {

    private val logger = LoggerFactory.getLogger(FileUploadFacade::class.java)

    fun uploadFile(file: MultipartFile, documentRequest: DocumentUploadRequest) {
        logger.info("Start uploading file: ${file.originalFilename}")
        val savedFileName = saveFile(documentRequest, file)
        logger.info("File has been saved as: $savedFileName")
        saveDocument(documentRequest, savedFileName, file)
        logger.info("File has been saved to database as: $savedFileName")
    }

    private fun saveFile(documentRequest: DocumentUploadRequest, file: MultipartFile): String =
        fileHandler.save(documentRequest.memberId, documentRequest.documentType, file)

    private fun saveDocument(documentRequest: DocumentUploadRequest, savedFileName: String, file: MultipartFile) {
        try {
            documentService.saveDocument(createDocument(documentRequest, savedFileName, file))
        } catch (e: Exception) {
            logger.error("Failed to save document. Deleting file", e)
            fileHandler.delete(documentRequest.memberId, documentRequest.documentType, savedFileName)
        }
    }

    private fun createDocument(documentRequest: DocumentUploadRequest, savedFileName: String, file: MultipartFile) =
        documentTransformer.toDocument(documentRequest, savedFileName, file)

}
