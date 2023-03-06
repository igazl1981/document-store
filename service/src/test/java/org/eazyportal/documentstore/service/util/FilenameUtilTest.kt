package org.eazyportal.documentstore.service.util

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import java.util.UUID

class FilenameUtilTest {

    private val filenameUtil = FilenameUtil()

    @Test
    fun `test getName should return a UUID string`() {

        val result = filenameUtil.getRandomFilename()

        assertNotNull(UUID.fromString(result))
    }
}
