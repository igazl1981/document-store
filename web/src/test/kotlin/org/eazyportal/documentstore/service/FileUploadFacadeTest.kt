package org.eazyportal.documentstore.service

import org.eazyportal.documentstore.service.document.DocumentService
import org.eazyportal.documentstore.service.transformer.DocumentTransformer
import org.eazyportal.documentstore.service.upload.FileHandler
import org.eazyportal.documentstore.test.utils.ModelUtils
import org.eazyportal.documentstore.test.utils.ModelUtils.documentType
import org.eazyportal.documentstore.test.utils.ModelUtils.getDocument
import org.eazyportal.documentstore.test.utils.ModelUtils.memberId
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.doNothing
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.springframework.mock.web.MockMultipartFile

@ExtendWith(MockitoExtension::class)
class FileUploadFacadeTest {

    @Mock
    private lateinit var fileHandler: FileHandler

    @Mock
    private lateinit var documentTransformer: DocumentTransformer

    @Mock
    private lateinit var documentService: DocumentService


    @InjectMocks
    private lateinit var fileUploadFacade: FileUploadFacade

    @Test
    fun `test uploadFile should call handler`() {
        val file = MockMultipartFile("mocked-upload-file.txt", "Content".toByteArray())
        val request = ModelUtils.getDocumentUploadRequest()
        val savedFilename = "saved-file-name.txt"
        val document = getDocument()
        whenever(fileHandler.save(memberId, documentType, file)).thenReturn(savedFilename)
        whenever(documentTransformer.toDocument(request, savedFilename, file)).thenReturn(document)
        doNothing().whenever(documentService).saveDocument(document)

        fileUploadFacade.uploadFile(file, request)

        verify(fileHandler).save(memberId, documentType, file)
        verify(documentTransformer).toDocument(request, savedFilename, file)
        verify(documentService).saveDocument(document)
    }

    @Test
    fun `test uploadFile should try deleting file when save failed`() {
        val file = MockMultipartFile("mocked-upload-file.txt", "Content".toByteArray())
        val request = ModelUtils.getDocumentUploadRequest()
        val savedFilename = "saved-file-name.txt"
        val document = getDocument()
        whenever(fileHandler.save(memberId, documentType, file)).thenReturn(savedFilename)
        whenever(documentTransformer.toDocument(request, savedFilename, file)).thenReturn(document)
        whenever(documentService.saveDocument(document)).thenThrow(RuntimeException("Save failed."))
        doNothing().whenever(fileHandler).delete(memberId, documentType, savedFilename)

        fileUploadFacade.uploadFile(file, request)

        verify(fileHandler).save(memberId, documentType, file)
        verify(documentTransformer).toDocument(request, savedFilename, file)
        verify(documentService).saveDocument(document)
        verify(fileHandler).delete(memberId, documentType, savedFilename)
    }

}
