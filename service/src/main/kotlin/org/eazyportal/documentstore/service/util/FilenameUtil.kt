package org.eazyportal.documentstore.service.util

import org.springframework.stereotype.Component
import java.util.UUID

@Component
class FilenameUtil {

    fun getRandomFilename() = UUID.randomUUID().toString()
}
