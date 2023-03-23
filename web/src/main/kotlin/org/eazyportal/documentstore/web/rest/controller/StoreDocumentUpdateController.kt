package org.eazyportal.documentstore.web.rest.controller

import org.eazyportal.documentstore.web.rest.model.DocumentUpdateRequest
import org.eazyportal.documentstore.web.rest.model.StoredDocument
import org.eazyportal.documentstore.web.service.DocumentUpdateFacade
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/documents")
class StoreDocumentUpdateController(private val documentUpdateFacade: DocumentUpdateFacade) {

    @PatchMapping("/{documentId}")
    fun updateDocument(
        @PathVariable documentId: String,
        @RequestBody documentUpdateRequest: DocumentUpdateRequest
    ): StoredDocument? {
        return documentUpdateFacade.updateDocument(documentId, documentUpdateRequest)
    }
}
