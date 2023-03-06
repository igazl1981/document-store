package org.eazyportal.documentstore.web.rest.controller

import jakarta.validation.Valid
import org.eazyportal.core.web.rest.controller.EazyRestController
import org.eazyportal.documentstore.service.FileUploadFacade
import org.eazyportal.documentstore.web.rest.model.DocumentUploadRequest
import org.springframework.http.HttpStatus.CREATED
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/v1/documents")
@Validated
class FileUploadRestController(private val fileUploadFacade: FileUploadFacade) : EazyRestController {

    @PostMapping(consumes = ["multipart/form-data"])
    @ResponseStatus(CREATED)
    fun uploadDocument(
        @RequestPart("file") file: MultipartFile,
        @Valid @RequestPart("document") document: DocumentUploadRequest,
    ) {
        fileUploadFacade.uploadFile(file, document)
    }

}
