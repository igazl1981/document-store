package org.eazyportal.documentstore.service.document

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration
import org.eazyportal.documentstore.dao.model.StoredDocumentEntityFixtureValues.STORED_DOCUMENT
import org.eazyportal.documentstore.dao.model.StoredDocumentEntity
import org.eazyportal.documentstore.dao.repository.StoredDocumentRepository
import org.eazyportal.documentstore.service.document.model.DocumentFixtureValues.DOCUMENT
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
        val captor = argumentCaptor<StoredDocumentEntity>()
        whenever(storedDocumentRepository.save(captor.capture())).thenReturn(STORED_DOCUMENT)

        documentService.saveDocument(DOCUMENT)

        verify(storedDocumentRepository).save(captor.capture())
        val newStoredDocument = captor.firstValue
        assertThat(newStoredDocument).usingRecursiveComparison(getRecursionConfig()).isEqualTo(STORED_DOCUMENT)
        assertThat(newStoredDocument.uploadedAt).isAfter(testStartTime)
        assertThat(newStoredDocument.modifiedAt).isAfter(testStartTime)
    }

    private fun getRecursionConfig() =
        RecursiveComparisonConfiguration.builder().withIgnoredFields("id", "modifiedAt", "uploadedAt").build()

}
