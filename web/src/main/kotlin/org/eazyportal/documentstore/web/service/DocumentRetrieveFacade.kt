package org.eazyportal.documentstore.web.service

import org.eazyportal.documentstore.service.document.DocumentService
import org.eazyportal.documentstore.web.rest.model.StoredDocument
import org.eazyportal.documentstore.web.service.transformer.DocumentTransformer
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class DocumentRetrieveFacade(
    private val documentService: DocumentService, private val documentTransformer: DocumentTransformer
) {

    fun getAllDocuments(memberId: UUID, filterOptions: Map<String, String>, pageable: Pageable): Page<StoredDocument> =
        documentService.getAllDocuments(memberId, filterOptions, pageable).map(documentTransformer::toDto)

    fun getDocument(documentId: String): StoredDocument =
        documentService.getDocument(documentId).let(documentTransformer::toDto)
}
