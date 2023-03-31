package org.eazyportal.documentstore.service.document

import org.assertj.core.api.Assertions
import org.bson.types.ObjectId
import org.eazyportal.documentstore.CommonFixtureValues.DOCUMENT_TYPE_ID
import org.eazyportal.documentstore.CommonFixtureValues.DOCUMENT_TYPE_NAME
import org.eazyportal.documentstore.CommonFixtureValues.INVALID_ID
import org.eazyportal.documentstore.dao.model.DocumentTypeEntity
import org.eazyportal.documentstore.dao.model.DocumentTypeEntityFixtureValues.DOCUMENT_TYPE
import org.eazyportal.documentstore.dao.repository.DocumentTypeRepository
import org.eazyportal.documentstore.service.document.exception.DocumentTypeNotFoundException
import org.eazyportal.documentstore.service.document.exception.InvalidIdRepresentationException
import org.eazyportal.documentstore.service.document.model.DocumentFixtureValues.DOCUMENT_TYPE_UPDATED_NAME
import org.eazyportal.documentstore.service.document.model.DocumentFixtureValues.TYPE_INSERT
import org.eazyportal.documentstore.service.document.model.DocumentFixtureValues.TYPE_UPDATE
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever
import java.util.Optional

@ExtendWith(MockitoExtension::class)
class DocumentTypeServiceTest {

    @Mock
    private lateinit var documentTypeRepository: DocumentTypeRepository

    @InjectMocks
    private lateinit var documentTypeService: DocumentTypeService

    @Test
    fun `test getAll`() {
        whenever(documentTypeRepository.findAll()).thenReturn(listOf(DOCUMENT_TYPE))

        val result = documentTypeService.getAll()

        verify(documentTypeRepository).findAll()
        val expected = listOf(DOCUMENT_TYPE)
        Assertions.assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `test getById`() {
        val id = ObjectId(DOCUMENT_TYPE_ID)
        whenever(documentTypeRepository.findById(id)).thenReturn(Optional.of(DOCUMENT_TYPE))

        val result = documentTypeService.getById(DOCUMENT_TYPE_ID)

        verify(documentTypeRepository).findById(id)
        val expected = DOCUMENT_TYPE
        Assertions.assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `test getById should throw exception when ID is invalid`() {

        assertThrows<InvalidIdRepresentationException> { documentTypeService.getById(INVALID_ID) }

        verifyNoInteractions(documentTypeRepository)
    }

    @Test
    fun `test getById should return null when not found`() {
        val id = ObjectId(DOCUMENT_TYPE_ID)
        whenever(documentTypeRepository.findById(id)).thenReturn(Optional.empty())

        val result = documentTypeService.getById(DOCUMENT_TYPE_ID)

        verify(documentTypeRepository).findById(id)
        Assertions.assertThat(result).isNull()
    }

    @Test
    fun `test add`() {
        val newTypeCaptor = argumentCaptor<DocumentTypeEntity>()
        whenever(documentTypeRepository.insert(newTypeCaptor.capture())).thenReturn(DOCUMENT_TYPE)

        val result = documentTypeService.add(TYPE_INSERT)

        verify(documentTypeRepository).insert(newTypeCaptor.capture())
        val newType = newTypeCaptor.firstValue
        Assertions.assertThat(newType.name).isEqualTo(DOCUMENT_TYPE_NAME)
        Assertions.assertThat(result).isEqualTo(DOCUMENT_TYPE)
    }

    @Test
    fun `test update`() {
        val id = ObjectId(DOCUMENT_TYPE_ID)
        val updatedEntity = DocumentTypeEntity(DOCUMENT_TYPE_UPDATED_NAME, id)
        whenever(documentTypeRepository.findById(id)).thenReturn(Optional.of(DOCUMENT_TYPE))
        whenever(documentTypeRepository.save(updatedEntity)).thenReturn(updatedEntity)

        val result = documentTypeService.update(DOCUMENT_TYPE_ID, TYPE_UPDATE)

        verify(documentTypeRepository).findById(id)
        verify(documentTypeRepository).save(updatedEntity)
        Assertions.assertThat(result).isEqualTo(updatedEntity)
    }

    @Test
    fun `test update should throw exception when document type is not found`() {
        val id = ObjectId(DOCUMENT_TYPE_ID)
        whenever(documentTypeRepository.findById(id)).thenReturn(Optional.empty())

        assertThrows<DocumentTypeNotFoundException> {
            documentTypeService.update(DOCUMENT_TYPE_ID, TYPE_UPDATE)
        }

        verify(documentTypeRepository).findById(id)
        verifyNoMoreInteractions(documentTypeRepository)
    }

    @Test
    fun `test delete should call repository`() {

        documentTypeService.delete(DOCUMENT_TYPE)

        verify(documentTypeRepository).delete(DOCUMENT_TYPE)
    }
}
