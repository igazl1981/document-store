package org.eazyportal.documentstore.service.upload

import org.eazyportal.documentstore.service.util.FilenameUtil
import org.slf4j.LoggerFactory
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

@Component
@Order(0)
class NoOpFileHandler(private val filenameUtil: FilenameUtil) : FileHandler {

    private val logger = LoggerFactory.getLogger(NoOpFileHandler::class.java)

    override fun save(memberId: UUID, documentType: String, file: MultipartFile): String {
        logger.info("NoOpFileHandler handles the ${file.originalFilename} for member: $memberId")
        return "${filenameUtil.getRandomFilename()}.txt"
    }

    override fun delete(memberId: UUID, documentType: String, filename: String) {
        logger.info("NoOpFileHandler deletes the ${filename} for member: $memberId")
    }

}
