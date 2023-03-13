package org.eazyportal.documentstore.test.utils

import org.eazyportal.documentstore.dao.model.DocumentStatus
import org.eazyportal.documentstore.service.document.model.Document
import org.eazyportal.documentstore.web.rest.model.DocumentUploadRequest
import org.eazyportal.documentstore.web.rest.model.StoredDocument
import org.springframework.mock.web.MockMultipartFile
import java.time.LocalDateTime
import java.util.UUID

object ModelUtils {

    const val documentType = "document-type"
    val memberId: UUID = UUID.fromString("0b1e0c6c-710b-4641-8530-ee9beb760db9")

    fun getDocument() = Document(
        memberId = memberId,
        name = "uploaded-file-display-name.txt",
        documentType = documentType,
        savedFilename = "saved-file-name.txt",
        originalFilename = "mock-uploaded-file.txt",
        metadata = getDocumentMetadata()
    )

    fun getMockedUploadedFile() =
        MockMultipartFile("mock-uploaded-file.txt", "mock-uploaded-file.txt", "text/plain", "Content".toByteArray())

    fun getDocumentUploadRequest() = DocumentUploadRequest(
        memberId = memberId,
        name = "uploaded-file-display-name.txt",
        documentType = documentType,
        metadata = getDocumentMetadata()
    )

    fun getDocumentMetadata() = mapOf("TaxReturnPeriod" to getTaxReturnPeriod())

    fun getStoredDocument() = StoredDocument(
        id = "String",
        documentType = "TaxReturnPeriod",
        displayName = "uploaded-file-display-name.txt",
        savedFilename = "saved-file-name.txt",
        originalFilename = "mock-uploaded-file.txt",
        status = DocumentStatus.PENDING,
        metadata = getDocumentMetadata(),
        owner = memberId,
        modifiedBy = memberId,
        modifiedAt = LocalDateTime.of(2023, 3, 13, 19, 38, 0),
        uploadedAt = LocalDateTime.of(2023, 3, 13, 19, 38, 0)
    )

    private fun getTaxReturnPeriod() = mapOf(
        "year" to "2023", "quarter" to "2", "month" to "APRIL"
    )

}
