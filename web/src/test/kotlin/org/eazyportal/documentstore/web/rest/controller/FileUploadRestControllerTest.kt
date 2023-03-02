package org.eazyportal.documentstore.web.rest.controller

import org.eazyportal.documentstore.service.FileUploadFacade
import org.eazyportal.documentstore.test.utils.ModelUtils.getDocumentUploadRequest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.doNothing
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.springframework.mock.web.MockMultipartFile

@ExtendWith(MockitoExtension::class)
class FileUploadRestControllerTest {

    @Mock
    private lateinit var fileUploadFacade: FileUploadFacade

    @InjectMocks
    private lateinit var fileUploadRestController: FileUploadRestController

    @Test
    fun `test uploadDocument should call facade`() {
        val file = MockMultipartFile("mocked-upload-file.txt", "Content".toByteArray())
        val request = getDocumentUploadRequest()
        doNothing().whenever(fileUploadFacade).uploadFile(file, request)

        fileUploadRestController.uploadDocument(file, request)

        verify(fileUploadFacade, times(1)).uploadFile(file, request)
    }
}
