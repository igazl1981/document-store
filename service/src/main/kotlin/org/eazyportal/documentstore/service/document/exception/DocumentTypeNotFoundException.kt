package org.eazyportal.documentstore.service.document.exception

class DocumentTypeNotFoundException(documentTypeId: String) : RuntimeException() {

    override val message: String = "The document type was not found by id: $documentTypeId"

}
