package org.eazyportal.documentstore.dao

import jakarta.persistence.Id
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document
import java.time.ZonedDateTime

@Document
data class Document(
    val name: String,

    @Id
    val id: ObjectId = ObjectId.get(),
    val uploadedAt: ZonedDateTime = ZonedDateTime.now(),
    val modifiedAt: ZonedDateTime = ZonedDateTime.now(),
)
