package org.eazyportal.documentstore.web.service

import org.eazyportal.documentstore.dao.model.StoredDocumentEntity
import org.eazyportal.documentstore.service.document.DocumentService
import org.eazyportal.documentstore.web.rest.model.DocumentUpdateRequest
import org.eazyportal.documentstore.web.rest.model.StoredDocument
import org.eazyportal.documentstore.web.service.transformer.DocumentTransformer
import org.springframework.stereotype.Service

@Service
class DocumentUpdateFacade(
    private val documentService: DocumentService, private val documentTransformer: DocumentTransformer
) {

    fun updateDocument(documentId: String, documentUpdateRequest: DocumentUpdateRequest): StoredDocument? {
        val document = documentService.getDocument(documentId)
        document.displayName = documentUpdateRequest.displayName
        documentUpdateRequest.metadata?.also { setMissingMetadata(document) }?.forEach { (key, value) ->
                updateDocumentMetadata(document, key, value)
            }

        return documentTransformer.toDto(documentService.saveDocument(document))
    }

    private fun updateDocumentMetadata(document: StoredDocumentEntity, key: String, value: Any?) {
        value
            ?.also { document.metadata?.set(key, it) }
            ?: document.metadata?.remove(key)
    }

    private fun setMissingMetadata(document: StoredDocumentEntity) {
        if (document.metadata == null) {
            document.metadata = mutableMapOf()
        }
    }
}
