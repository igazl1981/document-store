package org.eazyportal.documentstore.service.document

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration
import org.eazyportal.documentstore.CommonFixtureValues.MEMBER_ID
import org.eazyportal.documentstore.dao.model.StoredDocumentEntity
import org.eazyportal.documentstore.dao.model.StoredDocumentEntityFixtureValues.DEFAULT_PAGEABLE
import org.eazyportal.documentstore.dao.model.StoredDocumentEntityFixtureValues.STORED_DOCUMENT
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
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import java.time.LocalDateTime

@ExtendWith(MockitoExtension::class)
class DocumentServiceTest {

    @Mock
    private lateinit var storedDocumentRepository: StoredDocumentRepository

    @Mock
    private lateinit var mongoTemplate: MongoTemplate

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

    @Test
    fun `test getAllDocuments should use query with owner only when filterOptions is empty`() {
        val query = Query(Criteria.where("owner").`is`(MEMBER_ID))
        val storedDocuments = listOf(STORED_DOCUMENT)
        whenever(mongoTemplate.find(query.with(DEFAULT_PAGEABLE), StoredDocumentEntity::class.java)).thenReturn(storedDocuments)

        val result = documentService.getAllDocuments(MEMBER_ID, emptyMap(), DEFAULT_PAGEABLE)

        verify(mongoTemplate).find(query.with(DEFAULT_PAGEABLE), StoredDocumentEntity::class.java)
        assertThat(result.content).isEqualTo(storedDocuments)
        assertThat(result.pageable).isEqualTo(DEFAULT_PAGEABLE)
    }

    @Test
    fun `test getAllDocuments should use query with owner and filter options when filterOptions has values`() {
        val query = Query(Criteria.where("owner").`is`(MEMBER_ID).andOperator(Criteria.where("filter1").`is`("value1")))
        val storedDocuments = listOf(STORED_DOCUMENT)
        val filterOptions = mapOf("filter1" to "value1")
        whenever(mongoTemplate.find(query.with(DEFAULT_PAGEABLE), StoredDocumentEntity::class.java)).thenReturn(storedDocuments)

        val result = documentService.getAllDocuments(MEMBER_ID, filterOptions, DEFAULT_PAGEABLE)

        verify(mongoTemplate).find(query.with(DEFAULT_PAGEABLE), StoredDocumentEntity::class.java)
        assertThat(result.content).isEqualTo(storedDocuments)
        assertThat(result.pageable).isEqualTo(DEFAULT_PAGEABLE)
    }

    private fun getRecursionConfig() =
        RecursiveComparisonConfiguration.builder().withIgnoredFields("id", "modifiedAt", "uploadedAt").build()

}
