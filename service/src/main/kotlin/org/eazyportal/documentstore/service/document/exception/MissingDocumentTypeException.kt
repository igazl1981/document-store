package org.eazyportal.documentstore.service.document.exception

class MissingDocumentTypeException(documentTypeId: String) : RuntimeException() {

    override val message: String = "Save failed. The document type is missing: $documentTypeId"

}
