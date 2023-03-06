package org.eazyportal.documentstore.service.document

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration
import org.eazyportal.documentstore.dao.StoredDocument
import org.eazyportal.documentstore.dao.StoredDocumentRepository
import org.eazyportal.documentstore.test.utils.ModelUtils.getDocument
import org.eazyportal.documentstore.test.utils.ModelUtils.getStoredDocument
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.time.LocalDateTime

@ExtendWith(MockitoExtension::class)
class DocumentServiceTest {

    @Mock
    private lateinit var storedDocumentRepository: StoredDocumentRepository

    @InjectMocks
    private lateinit var documentService: DocumentService

    @Test
    fun `test saveDocument`() {
        val testStartTime = LocalDateTime.now()
        val document = getDocument()
        val savedDocument = getStoredDocument()
        val captor = argumentCaptor<StoredDocument>()
        whenever(storedDocumentRepository.save(captor.capture())).thenReturn(savedDocument)

        documentService.saveDocument(document)

        verify(storedDocumentRepository).save(captor.capture())
        val newStoredDocument = captor.firstValue
        assertThat(newStoredDocument).usingRecursiveComparison(getRecursionConfig()).isEqualTo(savedDocument)
        assertThat(newStoredDocument.uploadedAt).isAfter(testStartTime)
        assertThat(newStoredDocument.modifiedAt).isAfter(testStartTime)
    }

    private fun getRecursionConfig() =
        RecursiveComparisonConfiguration.builder().withIgnoredFields("id", "modifiedAt", "uploadedAt").build()

}
