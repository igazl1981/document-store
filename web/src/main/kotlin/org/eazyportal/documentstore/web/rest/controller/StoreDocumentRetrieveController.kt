package org.eazyportal.documentstore.web.rest.controller

import org.eazyportal.documentstore.service.DocumentRetrieveFacade
import org.eazyportal.documentstore.web.rest.model.DocumentListResponse
import org.eazyportal.documentstore.web.rest.parameter.ParameterParser
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/v1/documents")
class StoreDocumentRetrieveController(
    private val documentRetrieveFacade: DocumentRetrieveFacade,
    private val parameterParser: ParameterParser
) {

    @GetMapping
    fun getMemberDocuments(
        @RequestParam
        memberId: UUID,
        @RequestParam requestParameters: Map<String, String>
    ): DocumentListResponse {
        val filterOptions = parameterParser.getFilterOptions(requestParameters)
        return DocumentListResponse(documentRetrieveFacade.getAllDocuments(memberId, filterOptions))
    }
}
