package org.eazyportal.documentstore.dao.model

import jakarta.persistence.Id
import org.bson.types.ObjectId
import org.eazyportal.core.dao.model.EazyEntity
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.util.UUID

@Document("storedDocuments")
class StoredDocumentEntity(
    @Id
    val id: ObjectId = ObjectId.get(),
) : EazyEntity<StoredDocumentEntity>() {

    var documentType: String? = null

    var metadata: Map<String, Any>? = null

    lateinit var displayName: String

    lateinit var savedFilename: String

    lateinit var originalFilename: String

    lateinit var status: DocumentStatus

    lateinit var owner: UUID

    lateinit var modifiedBy: UUID

    var modifiedAt: LocalDateTime = LocalDateTime.now()

    var uploadedAt: LocalDateTime = LocalDateTime.now()

}
