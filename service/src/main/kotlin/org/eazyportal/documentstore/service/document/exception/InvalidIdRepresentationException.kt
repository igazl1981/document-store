package org.eazyportal.documentstore.service.document.exception

class InvalidIdRepresentationException(id: String) : RuntimeException() {

    override val message: String = "The provided ID representation is not valid: $id"

}
