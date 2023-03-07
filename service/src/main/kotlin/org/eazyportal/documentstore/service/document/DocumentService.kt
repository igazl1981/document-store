package org.eazyportal.documentstore.service.document

import org.eazyportal.documentstore.dao.model.StoredDocumentEntity
import org.eazyportal.documentstore.dao.repository.StoredDocumentRepository
import org.eazyportal.documentstore.dao.model.DocumentStatus.PENDING
import org.eazyportal.documentstore.service.document.model.Document
import org.springframework.stereotype.Service

@Service
class DocumentService(private val storedDocumentRepository: StoredDocumentRepository) {

    fun saveDocument(document: Document) {
        val storedDocument = StoredDocumentEntity().apply {
            documentType = document.documentType
            metadata = document.metadata
            displayName = document.name
            savedFilename = document.savedFilename
            originalFilename = document.originalFilename
            status = PENDING
            owner = document.memberId
            modifiedBy = document.memberId
        }

        storedDocumentRepository.save(storedDocument)
    }

}
