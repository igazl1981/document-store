package org.eazyportal.documentstore.web.service

import org.assertj.core.api.Assertions
import org.eazyportal.documentstore.dao.model.DocumentTypeEntityFixtureValues.DOCUMENT_TYPE
import org.eazyportal.documentstore.service.document.DocumentService
import org.eazyportal.documentstore.service.document.DocumentTypeService
import org.eazyportal.documentstore.service.document.exception.DocumentTypeHasAssignedDocumentsException
import org.eazyportal.documentstore.service.document.exception.MissingDocumentTypeException
import org.eazyportal.documentstore.service.document.model.Type
import org.eazyportal.documentstore.test.utils.ModelUtils.documentType
import org.eazyportal.documentstore.test.utils.ModelUtils.documentTypeId
import org.eazyportal.documentstore.test.utils.ModelUtils.getDocumentType
import org.eazyportal.documentstore.test.utils.ModelUtils.getDocumentTypeCreateRequest
import org.eazyportal.documentstore.web.service.transformer.DocumentTypeTransformer
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions
import org.mockito.kotlin.whenever

@ExtendWith(MockitoExtension::class)
class DocumentTypeFacadeTest {

    @Mock
    private lateinit var documentService: DocumentService

    @Mock
    private lateinit var documentTypeService: DocumentTypeService

    @Mock
    private lateinit var documentTypeTransformer: DocumentTypeTransformer

    @InjectMocks
    private lateinit var documentTypeFacade: DocumentTypeFacade

    @Test
    fun `test getAll`() {
        whenever(documentTypeService.getAll()).thenReturn(listOf(DOCUMENT_TYPE))
        whenever(documentTypeTransformer.toDto(DOCUMENT_TYPE)).thenReturn(getDocumentType())

        val result = documentTypeFacade.getAll()

        verify(documentTypeService).getAll()
        verify(documentTypeTransformer).toDto(DOCUMENT_TYPE)
        val expected = listOf(getDocumentType())
        Assertions.assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `test getById`() {
        whenever(documentTypeService.getById(documentTypeId)).thenReturn(DOCUMENT_TYPE)
        whenever(documentTypeTransformer.toDto(DOCUMENT_TYPE)).thenReturn(getDocumentType())

        val result = documentTypeFacade.getById(documentTypeId)

        verify(documentTypeService).getById(documentTypeId)
        verify(documentTypeTransformer).toDto(DOCUMENT_TYPE)
        val expected = getDocumentType()
        Assertions.assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `test getById should return null when optional returned`() {
        whenever(documentTypeService.getById(documentTypeId)).thenReturn(null)

        val result = documentTypeFacade.getById(documentTypeId)

        verify(documentTypeService).getById(documentTypeId)
        verifyNoInteractions(documentTypeTransformer)
        Assertions.assertThat(result).isNull()
    }

    @Test
    fun `test add`() {
        val request = getDocumentTypeCreateRequest()
        val type = Type(documentType)
        whenever(documentTypeService.add(type)).thenReturn(DOCUMENT_TYPE)
        whenever(documentTypeTransformer.toDto(DOCUMENT_TYPE)).thenReturn(getDocumentType())

        val result = documentTypeFacade.add(request)

        verify(documentTypeService).add(type)
        verify(documentTypeTransformer).toDto(DOCUMENT_TYPE)
        val expected = getDocumentType()
        Assertions.assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `test update`() {
        val request = getDocumentTypeCreateRequest()
        val type = Type(documentType)
        whenever(documentTypeService.update(documentTypeId, type)).thenReturn(DOCUMENT_TYPE)
        whenever(documentTypeTransformer.toDto(DOCUMENT_TYPE)).thenReturn(getDocumentType())

        val result = documentTypeFacade.update(documentTypeId, request)

        verify(documentTypeService).update(documentTypeId, type)
        verify(documentTypeTransformer).toDto(DOCUMENT_TYPE)
        val expected = getDocumentType()
        Assertions.assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `test delete should throw exception when type not found`() {
        whenever(documentTypeService.getById(documentTypeId)).thenReturn(null)

        assertThrows<MissingDocumentTypeException> { documentTypeFacade.delete(documentTypeId) }

        verify(documentTypeService).getById(documentTypeId)
        verifyNoInteractions(documentTypeTransformer, documentService)
    }

    @Test
    fun `test delete should throw exception when documentType has assigned documents`() {
        whenever(documentTypeService.getById(documentTypeId)).thenReturn(DOCUMENT_TYPE)
        whenever(documentService.countByType(DOCUMENT_TYPE)).thenReturn(1)

        assertThrows<DocumentTypeHasAssignedDocumentsException> { documentTypeFacade.delete(documentTypeId) }

        verify(documentTypeService).getById(documentTypeId)
        verify(documentService).countByType(DOCUMENT_TYPE)
        verifyNoInteractions(documentTypeTransformer)
    }

    @Test
    fun `test delete should call repository to delete when there is no assigned document`() {
        whenever(documentTypeService.getById(documentTypeId)).thenReturn(DOCUMENT_TYPE)
        whenever(documentService.countByType(DOCUMENT_TYPE)).thenReturn(0)

        documentTypeFacade.delete(documentTypeId)

        verify(documentTypeService).getById(documentTypeId)
        verify(documentService).countByType(DOCUMENT_TYPE)
        verify(documentTypeService).delete(DOCUMENT_TYPE)
        verifyNoInteractions(documentTypeTransformer)
    }

}
