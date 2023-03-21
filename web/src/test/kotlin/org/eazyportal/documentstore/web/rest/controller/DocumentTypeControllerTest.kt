package org.eazyportal.documentstore.web.rest.controller

import org.assertj.core.api.Assertions
import org.eazyportal.documentstore.test.utils.ModelUtils.documentTypeId
import org.eazyportal.documentstore.test.utils.ModelUtils.getDocumentType
import org.eazyportal.documentstore.web.rest.model.DocumentTypeCreateRequest
import org.eazyportal.documentstore.web.rest.model.DocumentTypeListResponse
import org.eazyportal.documentstore.web.service.DocumentTypeFacade
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExtendWith(MockitoExtension::class)
class DocumentTypeControllerTest {

    @Mock
    private lateinit var documentTypeFacade: DocumentTypeFacade

    @InjectMocks
    private lateinit var documentTypeController: DocumentTypeController

    @Test
    fun `test getAll`() {
        whenever(documentTypeFacade.getAll()).thenReturn(listOf(getDocumentType()))

        val result = documentTypeController.getAll()

        verify(documentTypeFacade).getAll()
        val expected = DocumentTypeListResponse(listOf(getDocumentType()))
        Assertions.assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `test getById`() {
        whenever(documentTypeFacade.getById(documentTypeId)).thenReturn(getDocumentType())

        val result = documentTypeController.getById(documentTypeId)

        verify(documentTypeFacade).getById(documentTypeId)
        val expected = getDocumentType()
        Assertions.assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `test add`() {
        val request = DocumentTypeCreateRequest("document-type-1")
        whenever(documentTypeFacade.add(request)).thenReturn(getDocumentType())

        val result = documentTypeController.add(request)

        verify(documentTypeFacade).add(request)
        val expected = getDocumentType()
        Assertions.assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `test update`() {
        val request = DocumentTypeCreateRequest("document-type-1")
        whenever(documentTypeFacade.update(documentTypeId, request)).thenReturn(getDocumentType())

        val result = documentTypeController.update(documentTypeId, request)

        verify(documentTypeFacade).update(documentTypeId, request)
        val expected = getDocumentType()
        Assertions.assertThat(result).isEqualTo(expected)
    }
}
