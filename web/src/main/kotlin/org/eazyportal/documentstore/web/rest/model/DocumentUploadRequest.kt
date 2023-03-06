package org.eazyportal.documentstore.web.rest.model

import jakarta.validation.constraints.NotNull
import org.hibernate.validator.constraints.Length
import java.util.UUID

data class DocumentUploadRequest(
    @field:NotNull
    val memberId: UUID,
    @field:NotNull
    @field:Length(min = 5, max = 255)
    val name: String,
    @field:Length(min = 10, max = 50)
    val documentType: String,
    val metadata: Map<String, Any>?,
)
