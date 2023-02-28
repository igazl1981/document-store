package org.eazyportal.documentstore.web.rest.controller

import jakarta.validation.Valid
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import org.eazyportal.core.web.rest.controller.EazyRestController
import org.hibernate.validator.constraints.Length
import org.springframework.http.HttpStatus.CREATED
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.time.Month
import java.util.UUID


@RestController
@RequestMapping("/v1/documents")
@Validated
class FileUploadRestController : EazyRestController {

    // TODO: When file is not sent org.springframework.web.multipart.support.MissingServletRequestPartException is thrown
    // Should we handle it separately??? It results a 500 response which is not completely valid.
    // TODO: Request is not validated on the JSON !!!!!!!
    @PostMapping(consumes = ["multipart/form-data"])
    @ResponseStatus(CREATED)
    fun uploadDocument(
        @RequestPart("file") file: MultipartFile,
        @Valid @RequestPart("document") document: DocumentUploadRequest,
    ) {
        println(document)
    }

}

data class DocumentUploadRequest(
    @NotNull
    val memberId: UUID,
    @NotNull
    @Length(min = 5, max = 255)
    val name: String,
    @Length(min = 10, max = 50)
    val documentType: String,
    val metadata: DocumentMetadata?,
)

data class DocumentMetadata(
    val taxReturnPeriod: TaxReturnPeriod?
)

data class TaxReturnPeriod(
    @NotNull
    @Max(9999)
    @Min(1900)
    val year: Int,
    @Max(4)
    @Min(1)
    val quarter: Int?,
    val month: Month?
)
