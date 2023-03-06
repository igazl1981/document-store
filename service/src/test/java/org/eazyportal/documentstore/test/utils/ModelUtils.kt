package org.eazyportal.documentstore.test.utils

import org.bson.types.ObjectId
import org.eazyportal.documentstore.dao.DocumentStatus
import org.eazyportal.documentstore.dao.StoredDocument
import org.eazyportal.documentstore.service.document.model.Document
import java.time.Instant
import java.time.LocalDateTime
import java.util.Date
import java.util.UUID

object ModelUtils {

    const val documentType = "document-type"
    val memberId: UUID = UUID.fromString("0b1e0c6c-710b-4641-8530-ee9beb760db9")

    fun getDocument() = Document(
        memberId = memberId,
        name = "DisplayName",
        documentType = documentType,
        savedFilename = "savedFilename.txt",
        originalFilename = "originalFilename.txt",
        metadata = null
    )

    fun getStoredDocument() = StoredDocument(
        documentType = documentType,
        metadata = null,
        displayName = "DisplayName",
        savedFilename = "savedFilename.txt",
        originalFilename = "originalFilename.txt",
        status = DocumentStatus.PENDING,
        owner = memberId,
        modifiedBy = memberId,
        id = ObjectId.getSmallestWithDate(Date.from(Instant.parse("2023-03-04T10:00:00Z"))),
        modifiedAt = LocalDateTime.parse("2023-03-04T11:00:00"),
        uploadedAt = LocalDateTime.parse("2023-03-04T10:00:00")
    )

}
