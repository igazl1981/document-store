package org.eazyportal.documentstore.service

import org.eazyportal.documentstore.service.document.DocumentService
import org.eazyportal.documentstore.service.transformer.DocumentTransformer
import org.eazyportal.documentstore.web.rest.model.StoredDocument
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class DocumentRetrieveFacade(
    private val documentService: DocumentService, private val documentTransformer: DocumentTransformer
) {

    fun getAllDocuments(memberId: UUID, filterOptions: Map<String, String>): List<StoredDocument> =
        documentService.getAllDocuments(memberId, filterOptions).map(documentTransformer::toDto)
}
