package org.eazyportal.documentstore.service

import org.eazyportal.documentstore.service.upload.FileHandler
import org.eazyportal.documentstore.test.utils.ModelUtils
import org.eazyportal.documentstore.test.utils.ModelUtils.documentType
import org.eazyportal.documentstore.test.utils.ModelUtils.memberId
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.springframework.mock.web.MockMultipartFile

@ExtendWith(MockitoExtension::class)
class FileUploadFacadeTest {

    @Mock
    private lateinit var fileHandler: FileHandler

    @InjectMocks
    private lateinit var fileUploadFacade: FileUploadFacade

    @Test
    fun `test uploadFile should call handler`() {
        val file = MockMultipartFile("mocked-upload-file.txt", "Content".toByteArray())
        val request = ModelUtils.getDocumentUploadRequest()
        val savedFilename = "saved-file-name.txt"
        whenever(fileHandler.handle(memberId, documentType, file)).thenReturn(savedFilename)

        fileUploadFacade.uploadFile(file, request)

        verify(fileHandler, times(1)).handle(memberId, documentType, file)
    }

}
