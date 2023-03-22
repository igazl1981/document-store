package org.eazyportal.documentstore.dao.model

import org.bson.types.ObjectId
import org.eazyportal.documentstore.CommonFixtureValues.DOCUMENT_NAME
import org.eazyportal.documentstore.CommonFixtureValues.MEMBER_ID
import org.eazyportal.documentstore.CommonFixtureValues.METADATA
import org.eazyportal.documentstore.CommonFixtureValues.ORIGINAL_FILENAME
import org.eazyportal.documentstore.CommonFixtureValues.SAVED_FILENAME
import org.eazyportal.documentstore.dao.model.DocumentStatus.PENDING
import org.eazyportal.documentstore.dao.model.DocumentTypeEntityFixtureValues.DOCUMENT_TYPE
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import java.time.Instant
import java.time.LocalDateTime
import java.util.Date

object StoredDocumentEntityFixtureValues {

    val STORED_DOCUMENT = StoredDocumentEntity(
        documentType = DOCUMENT_TYPE,
        displayName = DOCUMENT_NAME,
        savedFilename = SAVED_FILENAME,
        originalFilename = ORIGINAL_FILENAME,
        status = PENDING,
        owner = MEMBER_ID,
        modifiedBy = MEMBER_ID,
        id = ObjectId.getSmallestWithDate(Date.from(Instant.parse("2023-03-04T10:00:00Z"))),
        metadata = METADATA,
        modifiedAt = LocalDateTime.parse("2023-03-04T11:00:00"),
        uploadedAt = LocalDateTime.parse("2023-03-04T10:00:00"),
    )

    val DEFAULT_PAGEABLE = PageRequest.of(0, 13, Sort.unsorted())

}
