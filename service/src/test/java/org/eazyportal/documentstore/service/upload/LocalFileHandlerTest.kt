package org.eazyportal.documentstore.service.upload

import org.assertj.core.api.Assertions.assertThat
import org.eazyportal.documentstore.service.util.FilenameUtil
import org.eazyportal.documentstore.test.utils.ModelUtils.documentType
import org.eazyportal.documentstore.test.utils.ModelUtils.memberId
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.doNothing
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.springframework.mock.web.MockMultipartFile
import java.io.File

@ExtendWith(MockitoExtension::class)
class LocalFileHandlerTest {

    private val savePath = "save-path"

    @Mock
    private lateinit var filenameUtil: FilenameUtil

    private lateinit var localFileHandler: LocalFileHandler

    @BeforeEach
    fun setUp() {
        localFileHandler = LocalFileHandler(savePath, filenameUtil)
    }

    @Test
    fun `test handle should save the file and return saved filename with the original extension`() {
        val file: MockMultipartFile = mock()
        val captor = argumentCaptor<File>()
        whenever(file.originalFilename).thenReturn("mock-uploaded-file.txt")
        whenever(filenameUtil.getRandomFilename()).thenReturn("random-filename")
        doNothing().whenever(file).transferTo(captor.capture())

        val result = localFileHandler.handle(memberId, documentType, file)

        assertThat(captor.firstValue.absoluteFile.toString())
            .endsWith("save-path\\0b1e0c6c-710b-4641-8530-ee9beb760db9\\document-type\\random-filename.txt")
        assertThat(result).isEqualTo("random-filename.txt")
    }

    @Test
    fun `test handle should save the file and return saved filename without extension`() {
        val file: MockMultipartFile = mock()
        val captor = argumentCaptor<File>()
        whenever(file.originalFilename).thenReturn("mock-uploaded-file")
        whenever(filenameUtil.getRandomFilename()).thenReturn("random-filename")
        doNothing().whenever(file).transferTo(captor.capture())

        val result = localFileHandler.handle(memberId, documentType, file)

        assertThat(captor.firstValue.absoluteFile.toString())
            .endsWith("save-path\\0b1e0c6c-710b-4641-8530-ee9beb760db9\\document-type\\random-filename")
        assertThat(result).isEqualTo("random-filename")
    }
}
