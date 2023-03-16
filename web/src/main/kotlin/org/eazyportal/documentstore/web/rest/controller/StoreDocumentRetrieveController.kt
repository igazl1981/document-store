package org.eazyportal.documentstore.web.rest.controller

import org.eazyportal.documentstore.service.DocumentRetrieveFacade
import org.eazyportal.documentstore.web.rest.model.StoredDocument
import org.eazyportal.documentstore.web.rest.parameter.ParameterParser
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
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

    /**
     * Returns all documents for a specific member.
     *
     * In th request parameters the client can send the path of a node, with a `query.` prefix, and a value for filtering.
     *
     * eg: query.metadata.taxReturnPeriod.year: 2023, then all documents where `metadata.taxReturnPeriod.year` field is '2023' will be returned.
     *
     * The `Pageable` object is defaulted to [org.springframework.data.web.PageableHandlerMethodArgumentResolverSupport.DEFAULT_PAGE_REQUEST].
     *
     * The default values can be changed by adding `@PageableDefault` annotation to the parameter.
     */
    @GetMapping
    fun getMemberDocuments(
        @RequestParam
        memberId: UUID,
        @RequestParam requestParameters: Map<String, String>,
        pageable: Pageable
    ): Page<StoredDocument> {
        val filterOptions = parameterParser.getFilterOptions(requestParameters)
        return documentRetrieveFacade.getAllDocuments(memberId, filterOptions, pageable)
    }
}
