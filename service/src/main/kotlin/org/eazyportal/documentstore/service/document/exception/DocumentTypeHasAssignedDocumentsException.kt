package org.eazyportal.documentstore.service.document.exception

class DocumentTypeHasAssignedDocumentsException(documentTypeId: String, name: String) : RuntimeException() {

    override val message: String = "Document type $name ($documentTypeId) cannot be deleted due to existing assigned documents."

}
