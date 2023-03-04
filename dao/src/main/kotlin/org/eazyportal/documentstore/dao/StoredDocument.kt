package org.eazyportal.documentstore.dao

import jakarta.persistence.Id
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.util.UUID

@Document("storedDocuments")
data class StoredDocument(
    val documentType: String?,
    val metadata: Map<String, Any>?,
    val displayName: String,
    val savedFilename: String,
    val originalFilename: String,
    val status: DocumentStatus,
    val owner: UUID,
    val modifiedBy: UUID,
    @Id
    val id: ObjectId = ObjectId.get(),
    val modifiedAt: LocalDateTime = LocalDateTime.now(),
    val uploadedAt: LocalDateTime = LocalDateTime.now(),
)
