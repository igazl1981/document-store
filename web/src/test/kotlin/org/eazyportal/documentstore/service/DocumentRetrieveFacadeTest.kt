package org.eazyportal.documentstore.service

import org.assertj.core.api.Assertions.assertThat
import org.eazyportal.documentstore.dao.model.StoredDocumentEntityFixtureValues.STORED_DOCUMENT
import org.eazyportal.documentstore.service.document.DocumentService
import org.eazyportal.documentstore.service.transformer.DocumentTransformer
import org.eazyportal.documentstore.test.utils.ModelUtils.getStoredDocument
import org.eazyportal.documentstore.test.utils.ModelUtils.memberId
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

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
        whenever(documentService.getAllDocuments(memberId, filterOptions)).thenReturn(listOf(STORED_DOCUMENT))
        whenever(documentTransformer.toDto(STORED_DOCUMENT)).thenReturn(getStoredDocument())

        val result = documentRetrieveFacade.getAllDocuments(memberId, filterOptions)

        verify(documentService).getAllDocuments(memberId, filterOptions)
        verify(documentTransformer).toDto(STORED_DOCUMENT)
        assertThat(result).isEqualTo(listOf(getStoredDocument()))
    }
}
