package org.eazyportal.documentstore.service.upload

import org.assertj.core.api.Assertions.assertThat
import org.eazyportal.documentstore.CommonFixtureValues.DOCUMENT_TYPE
import org.eazyportal.documentstore.CommonFixtureValues.MEMBER_ID
import org.eazyportal.documentstore.service.util.FilenameUtil
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.springframework.mock.web.MockMultipartFile

@ExtendWith(MockitoExtension::class)
class NoOpFileHandlerTest {

    @Mock
    private lateinit var filenameUtil: FilenameUtil

    @InjectMocks
    private lateinit var noOpFileHandler: NoOpFileHandler

    @Test
    fun `test handle`() {
        val file: MockMultipartFile = mock()

        whenever(filenameUtil.getRandomFilename()).thenReturn("random-filename")

        val result = noOpFileHandler.save(MEMBER_ID, DOCUMENT_TYPE, file)

        assertThat(result).isEqualTo("random-filename.txt")
    }

}
