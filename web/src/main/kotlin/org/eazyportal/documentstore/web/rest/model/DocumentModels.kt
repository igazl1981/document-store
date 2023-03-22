package org.eazyportal.documentstore.web.rest.model

import org.eazyportal.documentstore.dao.model.DocumentStatus
import java.time.LocalDateTime
import java.util.UUID

data class StoredDocument(
    val id: String,
    val documentType: String?,
    val displayName: String,
    val savedFilename: String,
    val originalFilename: String,
    val status: DocumentStatus,
    val metadata: Map<String, Any>?,
    val owner: UUID,
    val modifiedBy: UUID,
    val modifiedAt: LocalDateTime,
    val uploadedAt: LocalDateTime
)
