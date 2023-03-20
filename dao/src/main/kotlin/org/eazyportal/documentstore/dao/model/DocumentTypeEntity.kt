package org.eazyportal.documentstore.dao.model

import jakarta.persistence.Id
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document("documentTypes")
data class DocumentTypeEntity(
    @Indexed(unique = true)
    var name: String,
    @Id
    val id: ObjectId = ObjectId.get()
)
