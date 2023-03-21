package org.eazyportal.documentstore.web.service.transformer

import org.assertj.core.api.Assertions.assertThat
import org.eazyportal.documentstore.CommonFixtureValues.DOCUMENT_NAME
import org.eazyportal.documentstore.CommonFixtureValues.DOCUMENT_TYPE_NAME
import org.eazyportal.documentstore.CommonFixtureValues.MEMBER_ID
import org.eazyportal.documentstore.CommonFixtureValues.METADATA
import org.eazyportal.documentstore.CommonFixtureValues.ORIGINAL_FILENAME
import org.eazyportal.documentstore.CommonFixtureValues.SAVED_FILENAME
import org.eazyportal.documentstore.dao.model.DocumentStatus
import org.eazyportal.documentstore.dao.model.StoredDocumentEntityFixtureValues.STORED_DOCUMENT
import org.eazyportal.documentstore.service.document.model.Document
import org.eazyportal.documentstore.test.utils.ModelUtils.documentType
import org.eazyportal.documentstore.test.utils.ModelUtils.getDocumentMetadata
import org.eazyportal.documentstore.test.utils.ModelUtils.getDocumentUploadRequest
import org.eazyportal.documentstore.test.utils.ModelUtils.getMockedUploadedFile
import org.eazyportal.documentstore.test.utils.ModelUtils.memberId
import org.eazyportal.documentstore.web.rest.model.StoredDocument
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class DocumentTransformerTest {

    private val documentTransformer = DocumentTransformer()

    @Test
    fun `test toDocument`() {
        val request = getDocumentUploadRequest()
        val savedFileName = "saved-file.txt"
        val result = documentTransformer.toDocument(request, savedFileName, getMockedUploadedFile())

        val expected = Document(
            memberId = memberId,
            name = "uploaded-file-display-name.txt",
            documentType = documentType,
            savedFilename = savedFileName,
            originalFilename = "mock-uploaded-file.txt",
            metadata = getDocumentMetadata()
        )
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `test toDto`() {

        val result = documentTransformer.toDto(STORED_DOCUMENT)

        val expected = StoredDocument(
            id = "640316a00000000000000000",
            documentType = DOCUMENT_TYPE_NAME,
            displayName = DOCUMENT_NAME,
            savedFilename = SAVED_FILENAME,
            originalFilename = ORIGINAL_FILENAME,
            status = DocumentStatus.PENDING,
            metadata = METADATA,
            owner = MEMBER_ID,
            modifiedBy = MEMBER_ID,
            modifiedAt = LocalDateTime.parse("2023-03-04T11:00:00"),
            uploadedAt = LocalDateTime.parse("2023-03-04T10:00:00")
        )
        assertThat(result).isEqualTo(expected)

    }
}
