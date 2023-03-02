package org.eazyportal.documentstore.service.upload.error


sealed class UploadException(message: String, cause: Throwable) : RuntimeException(message, cause)

class FileSaveFailedException(filePath: String, cause: Throwable) :
    UploadException("Could not save the file: $filePath", cause)
