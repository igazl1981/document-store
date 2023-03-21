package org.eazyportal.documentstore.service.document.exception

class DocumentTypeNotFoundException(documentTypeName: String) : RuntimeException() {

    override val message: String = "The document type was not found by name: $documentTypeName"

}
