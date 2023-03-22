package org.eazyportal.documentstore.service.document.model

import java.util.UUID

data class Document(
    val memberId: UUID,
    val name: String,
    val documentTypeId: String,
    val savedFilename: String,
    val originalFilename: String,
    val metadata: Map<String, Any>?
)
