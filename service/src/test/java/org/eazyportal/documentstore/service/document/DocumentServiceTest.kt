package org.eazyportal.documentstore.service.document

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration
import org.bson.types.ObjectId
import org.eazyportal.documentstore.CommonFixtureValues.DOCUMENT_ID_STRING
import org.eazyportal.documentstore.CommonFixtureValues.DOCUMENT_TYPE_ID
import org.eazyportal.documentstore.CommonFixtureValues.INVALID_ID
import org.eazyportal.documentstore.CommonFixtureValues.MEMBER_ID
import org.eazyportal.documentstore.dao.model.DocumentTypeEntityFixtureValues.DOCUMENT_TYPE
import org.eazyportal.documentstore.dao.model.StoredDocumentEntity
import org.eazyportal.documentstore.dao.model.StoredDocumentEntityFixtureValues.DEFAULT_PAGEABLE
import org.eazyportal.documentstore.dao.model.StoredDocumentEntityFixtureValues.STORED_DOCUMENT
import org.eazyportal.documentstore.dao.repository.StoredDocumentRepository
import org.eazyportal.documentstore.service.document.exception.DocumentNotFoundException
import org.eazyportal.documentstore.service.document.exception.InvalidIdRepresentationException
import org.eazyportal.documentstore.service.document.model.DocumentFixtureValues.DOCUMENT
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions
import org.mockito.kotlin.whenever
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import java.time.LocalDateTime
import java.util.Optional

@ExtendWith(MockitoExtension::class)
class DocumentServiceTest {

    @Mock
    private lateinit var documentTypeService: DocumentTypeService

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
        whenever(documentTypeService.getById(DOCUMENT_TYPE_ID)).thenReturn(DOCUMENT_TYPE)

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
        whenever(mongoTemplate.find(query.with(DEFAULT_PAGEABLE), StoredDocumentEntity::class.java))
            .thenReturn(storedDocuments)

        val result = documentService.getAllDocuments(MEMBER_ID, emptyMap(), DEFAULT_PAGEABLE)

        verify(mongoTemplate).find(query.with(DEFAULT_PAGEABLE), StoredDocumentEntity::class.java)
        verifyNoInteractions(documentTypeService, storedDocumentRepository)
        assertThat(result.content).isEqualTo(storedDocuments)
        assertThat(result.pageable).isEqualTo(DEFAULT_PAGEABLE)
    }

    @Test
    fun `test getAllDocuments should use query with owner and filter options when filterOptions has values`() {
        val query = Query(Criteria.where("owner").`is`(MEMBER_ID).andOperator(Criteria.where("filter1").`is`("value1")))
        val storedDocuments = listOf(STORED_DOCUMENT)
        val filterOptions = mapOf("filter1" to "value1")
        whenever(mongoTemplate.find(query.with(DEFAULT_PAGEABLE), StoredDocumentEntity::class.java))
            .thenReturn(storedDocuments)

        val result = documentService.getAllDocuments(MEMBER_ID, filterOptions, DEFAULT_PAGEABLE)

        verify(mongoTemplate).find(query.with(DEFAULT_PAGEABLE), StoredDocumentEntity::class.java)
        verifyNoInteractions(documentTypeService, storedDocumentRepository)
        assertThat(result.content).isEqualTo(storedDocuments)
        assertThat(result.pageable).isEqualTo(DEFAULT_PAGEABLE)
    }

    @Test
    fun `test getDocument should return document`() {
        whenever(storedDocumentRepository.findById(ObjectId(DOCUMENT_ID_STRING)))
            .thenReturn(Optional.of(STORED_DOCUMENT))

        val result = documentService.getDocument(DOCUMENT_ID_STRING)

        verify(storedDocumentRepository).findById(ObjectId(DOCUMENT_ID_STRING))
        verifyNoInteractions(documentTypeService, mongoTemplate)
        assertThat(result).isEqualTo(STORED_DOCUMENT)
    }

    @Test
    fun `test getDocument should throw exception when document is not found`() {
        whenever(storedDocumentRepository.findById(ObjectId(DOCUMENT_ID_STRING)))
            .thenReturn(Optional.empty())

        assertThrows<DocumentNotFoundException> {  documentService.getDocument(DOCUMENT_ID_STRING) }

        verify(storedDocumentRepository).findById(ObjectId(DOCUMENT_ID_STRING))
        verifyNoInteractions(documentTypeService, mongoTemplate)
    }

    @Test
    fun `test getDocument should throw exception when document ID is invalid`() {
        assertThrows<InvalidIdRepresentationException> {  documentService.getDocument(INVALID_ID) }

        verifyNoInteractions(storedDocumentRepository, documentTypeService, mongoTemplate)
    }

    private fun getRecursionConfig() =
        RecursiveComparisonConfiguration.builder().withIgnoredFields("id", "modifiedAt", "uploadedAt").build()

}
