package org.eazyportal.documentstore.web.rest.model

import jakarta.validation.constraints.NotNull
import org.eazyportal.core.api.request.model.EazyRequest
import org.hibernate.validator.constraints.Length

data class DocumentUpdateRequest(
    @field:NotNull
    @field:Length(min = 5, max = 255)
    val displayName: String,
    val metadata: Map<String, Any?>?,
): EazyRequest
