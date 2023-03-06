package org.eazyportal.documentstore.service.document

import org.eazyportal.documentstore.dao.DocumentStatus
import org.eazyportal.documentstore.dao.StoredDocument
import org.eazyportal.documentstore.dao.StoredDocumentRepository
import org.eazyportal.documentstore.service.document.model.Document
import org.springframework.stereotype.Service

@Service
class DocumentService(private val storedDocumentRepository: StoredDocumentRepository) {

    fun saveDocument(document: Document) {
        val storedDocument = StoredDocument(
            document.documentType,
            document.metadata,
            document.name,
            document.savedFilename,
            document.originalFilename,
            DocumentStatus.PENDING,
            document.memberId,
            document.memberId
        )
        storedDocumentRepository.save(storedDocument)
    }
}
