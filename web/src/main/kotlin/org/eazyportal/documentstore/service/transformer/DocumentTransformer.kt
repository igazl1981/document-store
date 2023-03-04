package org.eazyportal.documentstore.service.transformer

import org.eazyportal.documentstore.service.document.model.Document
import org.eazyportal.documentstore.web.rest.model.DocumentUploadRequest
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class DocumentTransformer {

    fun toDocument(documentUploadRequest: DocumentUploadRequest, savedFileName: String, file: MultipartFile) =
        with(documentUploadRequest) {
            Document(
                memberId, name, documentType, savedFileName, file.originalFilename!!, documentUploadRequest.metadata
            )
        }
}
