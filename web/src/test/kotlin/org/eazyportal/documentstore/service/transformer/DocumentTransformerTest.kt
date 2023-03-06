package org.eazyportal.documentstore.service.transformer

import org.assertj.core.api.Assertions.assertThat
import org.eazyportal.documentstore.service.document.model.Document
import org.eazyportal.documentstore.test.utils.ModelUtils.documentType
import org.eazyportal.documentstore.test.utils.ModelUtils.getDocumentMetadata
import org.eazyportal.documentstore.test.utils.ModelUtils.getDocumentUploadRequest
import org.eazyportal.documentstore.test.utils.ModelUtils.getMockedUploadedFile
import org.eazyportal.documentstore.test.utils.ModelUtils.memberId
import org.junit.jupiter.api.Test

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
}
