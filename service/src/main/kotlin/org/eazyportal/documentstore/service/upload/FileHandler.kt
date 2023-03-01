package org.eazyportal.documentstore.service.upload

import org.springframework.web.multipart.MultipartFile
import java.util.UUID

/**
 * The interface can be used for various implementations of FileHandler.
 *
 * The service has a default [NoOpFileHandler] which just returns a random filename for proper implementation.
 *
 * When a new implementation is required, then the class should use:
 * * `@Profile` annotation and use that profile only, otherwise multiple implementations will exist during running
 * * `@Primary` annotation for making the new implementation the `Primary` so when [NoOpFileHandler] and the new implementation exists next to each other Spring will use the `Primary`
 *
 * Check [LocalFileHandler] for example.
 */
interface FileHandler {

    fun handle(memberId: UUID, documentType: String, file: MultipartFile): String

}
