package org.eazyportal.documentstore.web.service.transformer

import org.eazyportal.documentstore.dao.model.StoredDocumentEntity
import org.eazyportal.documentstore.service.document.model.Document
import org.eazyportal.documentstore.web.rest.model.DocumentUploadRequest
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import org.eazyportal.documentstore.web.rest.model.StoredDocument as StoredDocumentDto

@Service
class DocumentTransformer {

    fun toDocument(documentUploadRequest: DocumentUploadRequest, savedFileName: String, file: MultipartFile) =
        with(documentUploadRequest) {
            Document(
                memberId, name, documentTypeId, savedFileName, file.originalFilename!!, documentUploadRequest.metadata?.toMutableMap()
            )
        }

    fun toDto(entity: StoredDocumentEntity): StoredDocumentDto = StoredDocumentDto(
        entity.id.toString(),
        entity.documentType.name,
        entity.displayName,
        entity.savedFilename,
        entity.originalFilename,
        entity.status,
        entity.metadata,
        entity.owner,
        entity.modifiedBy,
        entity.modifiedAt,
        entity.uploadedAt,
    )
}
