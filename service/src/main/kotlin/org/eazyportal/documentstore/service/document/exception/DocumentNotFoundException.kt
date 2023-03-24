package org.eazyportal.documentstore.service.document.exception

class DocumentNotFoundException(documentId: String) : RuntimeException() {

    override val message: String = "The document was not found by id: $documentId"

}
