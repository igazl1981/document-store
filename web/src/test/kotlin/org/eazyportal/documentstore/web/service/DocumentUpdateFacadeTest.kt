package org.eazyportal.documentstore.web.service

import org.assertj.core.api.Assertions.assertThat
import org.eazyportal.documentstore.CommonFixtureValues.DOCUMENT_ID_STRING
import org.eazyportal.documentstore.CommonFixtureValues.METADATA
import org.eazyportal.documentstore.dao.model.StoredDocumentEntityFixtureValues.STORED_DOCUMENT
import org.eazyportal.documentstore.service.document.DocumentService
import org.eazyportal.documentstore.test.utils.ModelUtils.getDocumentUpdateRequest
import org.eazyportal.documentstore.test.utils.ModelUtils.getStoredDocument
import org.eazyportal.documentstore.web.service.transformer.DocumentTransformer
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExtendWith(MockitoExtension::class)
class DocumentUpdateFacadeTest {

    @Mock
    private lateinit var documentService: DocumentService

    @Mock
    private lateinit var documentTransformer: DocumentTransformer

    @InjectMocks
    private lateinit var documentUpdateFacade: DocumentUpdateFacade

    @Test
    fun `test update should update display name only when metadata is empty`() {
        val storedDocument = STORED_DOCUMENT.copy(metadata = METADATA.toMutableMap())
        val documentUpdateRequest = getDocumentUpdateRequest()
        val updatedStoredDocument = STORED_DOCUMENT.copy(displayName = "NewDisplayName")
        val transformedUpdatedDocument = getStoredDocument()
        whenever(documentService.getDocument(DOCUMENT_ID_STRING)).thenReturn(storedDocument)
        whenever(documentService.saveDocument(updatedStoredDocument)).thenReturn(updatedStoredDocument)
        whenever(documentTransformer.toDto(updatedStoredDocument)).thenReturn(transformedUpdatedDocument)

        val result = documentUpdateFacade.updateDocument(DOCUMENT_ID_STRING, documentUpdateRequest)

        verify(documentService).getDocument(DOCUMENT_ID_STRING)
        verify(documentService).saveDocument(storedDocument)
        verify(documentTransformer).toDto(updatedStoredDocument)
        assertThat(result).isEqualTo(transformedUpdatedDocument)
    }

    @Test
    fun `test update should update existing metadata when metadata contains existing keys`() {
        val storedDocument = STORED_DOCUMENT.copy(metadata = METADATA.toMutableMap())
        val newTaxReturnPeriodMetadata = "TaxReturnPeriod" to mapOf("new-content-key" to "new-content-value")
        val documentUpdateRequest = getDocumentUpdateRequest().copy(metadata = mapOf(newTaxReturnPeriodMetadata))
        val updatedStoredDocument = STORED_DOCUMENT.copy(
            displayName = "NewDisplayName",
            metadata = mutableMapOf(newTaxReturnPeriodMetadata)
        )
        val transformedUpdatedDocument = getStoredDocument()
        whenever(documentService.getDocument(DOCUMENT_ID_STRING)).thenReturn(storedDocument)
        whenever(documentService.saveDocument(updatedStoredDocument)).thenReturn(updatedStoredDocument)
        whenever(documentTransformer.toDto(updatedStoredDocument)).thenReturn(transformedUpdatedDocument)

        val result = documentUpdateFacade.updateDocument(DOCUMENT_ID_STRING, documentUpdateRequest)

        verify(documentService).getDocument(DOCUMENT_ID_STRING)
        verify(documentService).saveDocument(storedDocument)
        verify(documentTransformer).toDto(updatedStoredDocument)
        assertThat(result).isEqualTo(transformedUpdatedDocument)
    }

    @Test
    fun `test update should delete existing metadata when metadata contains existing keys with null value`() {
        val storedDocument = STORED_DOCUMENT.copy(metadata = METADATA.toMutableMap())
        val newTaxReturnPeriodMetadata = "TaxReturnPeriod" to null
        val documentUpdateRequest = getDocumentUpdateRequest().copy(metadata = mapOf(newTaxReturnPeriodMetadata))
        val updatedStoredDocument = STORED_DOCUMENT.copy(
            displayName = "NewDisplayName",
            metadata = mutableMapOf()
        )
        val transformedUpdatedDocument = getStoredDocument()
        whenever(documentService.getDocument(DOCUMENT_ID_STRING)).thenReturn(storedDocument)
        whenever(documentService.saveDocument(updatedStoredDocument)).thenReturn(updatedStoredDocument)
        whenever(documentTransformer.toDto(updatedStoredDocument)).thenReturn(transformedUpdatedDocument)

        val result = documentUpdateFacade.updateDocument(DOCUMENT_ID_STRING, documentUpdateRequest)

        verify(documentService).getDocument(DOCUMENT_ID_STRING)
        verify(documentService).saveDocument(storedDocument)
        verify(documentTransformer).toDto(updatedStoredDocument)
        assertThat(result).isEqualTo(transformedUpdatedDocument)
    }

    @Test
    fun `test update should add the new metadata and keep existing when metadata contains only new keys`() {
        val storedDocument = STORED_DOCUMENT.copy(metadata = METADATA.toMutableMap())
        val newMetadataKey = "NewMetadataKey" to "NewMetadataValue"
        val documentUpdateRequest = getDocumentUpdateRequest().copy(metadata = mapOf(newMetadataKey))
        val updatedMetadata = mutableMapOf<String, Any>()
        updatedMetadata.putAll(METADATA)
        updatedMetadata["NewMetadataKey"] = "NewMetadataValue"
        val updatedStoredDocument = STORED_DOCUMENT.copy(
            displayName = "NewDisplayName",
            metadata = updatedMetadata
        )
        val transformedUpdatedDocument = getStoredDocument()
        whenever(documentService.getDocument(DOCUMENT_ID_STRING)).thenReturn(storedDocument)
        whenever(documentService.saveDocument(updatedStoredDocument)).thenReturn(updatedStoredDocument)
        whenever(documentTransformer.toDto(updatedStoredDocument)).thenReturn(transformedUpdatedDocument)

        val result = documentUpdateFacade.updateDocument(DOCUMENT_ID_STRING, documentUpdateRequest)

        verify(documentService).getDocument(DOCUMENT_ID_STRING)
        verify(documentService).saveDocument(storedDocument)
        verify(documentTransformer).toDto(updatedStoredDocument)
        assertThat(result).isEqualTo(transformedUpdatedDocument)
    }
}
