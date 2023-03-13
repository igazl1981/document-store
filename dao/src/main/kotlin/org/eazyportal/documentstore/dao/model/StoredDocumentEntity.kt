package org.eazyportal.documentstore.dao.model

import jakarta.persistence.Id
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.util.UUID

@Document("storedDocuments")
class StoredDocumentEntity(
    val documentType: String,
    var displayName: String,
    val savedFilename: String,
    val originalFilename: String,
    var status: DocumentStatus,
    val owner: UUID,
    var modifiedBy: UUID,

    @Id
    val id: ObjectId = ObjectId.get(),
    var metadata: Map<String, Any>? = null,
    var modifiedAt: LocalDateTime = LocalDateTime.now(),
    val uploadedAt: LocalDateTime = LocalDateTime.now(),
)
