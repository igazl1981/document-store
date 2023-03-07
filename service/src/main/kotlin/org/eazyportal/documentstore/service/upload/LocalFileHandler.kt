package org.eazyportal.documentstore.service.upload

import org.eazyportal.documentstore.service.upload.error.FileSaveFailedException
import org.eazyportal.documentstore.service.util.FilenameUtil
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.IOException
import java.nio.file.Path
import java.nio.file.Paths
import java.util.UUID
import kotlin.io.path.absolutePathString
import kotlin.io.path.createDirectories
import kotlin.io.path.notExists

@Component
@Order(1)
@Primary
@Profile("local")
class LocalFileHandler(
    @Value("\${eazyportal.file-handler.local.save-path}")
    private val savePath: String, private val filenameUtil: FilenameUtil
) : FileHandler {

    companion object {
        private val LOGGER = LoggerFactory.getLogger(LocalFileHandler::class.java)
    }

    override fun save(memberId: UUID, documentType: String, file: MultipartFile): String {
        LOGGER.info("Saving file: ${file.originalFilename}")
        val extension = getOriginalExtension(file)
        val storageFilename = getStorageFilename(extension)
        val filePath = getAndCreateFilePath(memberId, documentType)
        LOGGER.info("Directory is created: $filePath")
        val targetFile = getTargetFile(filePath, storageFilename)
        try {
            file.transferTo(targetFile)
        } catch (e: Exception) {
            LOGGER.error("Failed to save file: ${targetFile.absoluteFile}", e)
            when (e) {
                is IOException, is IllegalStateException -> throw FileSaveFailedException(
                    "$filePath${file.originalFilename}",
                    e
                )
            }
        }

        return storageFilename
    }

    override fun delete(memberId: UUID, documentType: String, filename: String) {
        val filePath = getFilePath(memberId, documentType)
        val targetFile = getTargetFile(filePath, filename)
        deleteFile(targetFile)
    }

    private fun deleteFile(targetFile: File) {
        try {
            targetFile.delete()
        } catch (e: Exception) {
            LOGGER.error("Failed to delete the file ${targetFile.absoluteFile}", e)
        }
    }

    private fun getOriginalExtension(file: MultipartFile): String? =
        file.originalFilename?.let(::extractExtension).takeIf { it != file.originalFilename }

    private fun extractExtension(filename: String) = filename.substring(filename.lastIndexOf(".") + 1)

    private fun getStorageFilename(extension: String?) = filenameUtil.getRandomFilename() + getExtension(extension)

    private fun getExtension(extension: String?) = extension?.let { ".$it" } ?: ""

    private fun getAndCreateFilePath(memberId: UUID, documentType: String) =
        getFilePath(memberId, documentType).also { path ->
            if (path.notExists()) {
                path.createDirectories()
            }
        }

    private fun getFilePath(memberId: UUID, documentType: String) =
        Paths.get(savePath, memberId.toString(), documentType)

    private fun getTargetFile(filePath: Path, storageFilename: String) =
        File(filePath.absolutePathString() + File.separator + storageFilename)

}
