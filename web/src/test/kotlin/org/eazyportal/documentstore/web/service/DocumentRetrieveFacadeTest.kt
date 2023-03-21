package org.eazyportal.documentstore.web.service

import org.assertj.core.api.Assertions.assertThat
import org.eazyportal.documentstore.dao.model.StoredDocumentEntityFixtureValues.DEFAULT_PAGEABLE
import org.eazyportal.documentstore.dao.model.StoredDocumentEntityFixtureValues.STORED_DOCUMENT
import org.eazyportal.documentstore.service.document.DocumentService
import org.eazyportal.documentstore.test.utils.ModelUtils.getStoredDocument
import org.eazyportal.documentstore.test.utils.ModelUtils.memberId
import org.eazyportal.documentstore.web.service.transformer.DocumentTransformer
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.springframework.data.domain.PageImpl

@ExtendWith(MockitoExtension::class)
class DocumentRetrieveFacadeTest {

    @Mock
    private lateinit var documentService: DocumentService

    @Mock
    private lateinit var documentTransformer: DocumentTransformer

    @InjectMocks
    private lateinit var documentRetrieveFacade: DocumentRetrieveFacade

    @Test
    fun `test getAllDocuments`() {
        val filterOptions = mapOf("filter1" to "value1")
        val pagedStoredDocuments = PageImpl(listOf(STORED_DOCUMENT))
        whenever(documentService.getAllDocuments(memberId, filterOptions, DEFAULT_PAGEABLE))
            .thenReturn(pagedStoredDocuments)
        whenever(documentTransformer.toDto(STORED_DOCUMENT)).thenReturn(getStoredDocument())

        val result = documentRetrieveFacade.getAllDocuments(memberId, filterOptions, DEFAULT_PAGEABLE)

        verify(documentService).getAllDocuments(memberId, filterOptions, DEFAULT_PAGEABLE)
        verify(documentTransformer).toDto(STORED_DOCUMENT)
        assertThat(result.content).isEqualTo(listOf(getStoredDocument()))
    }
}
