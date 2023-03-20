package org.eazyportal.documentstore.web.rest.controller

import org.assertj.core.api.Assertions.assertThat
import org.eazyportal.documentstore.CommonFixtureValues.MEMBER_ID
import org.eazyportal.documentstore.dao.model.StoredDocumentEntityFixtureValues.DEFAULT_PAGEABLE
import org.eazyportal.documentstore.test.utils.ModelUtils.getStoredDocument
import org.eazyportal.documentstore.web.rest.parameter.ParameterParser
import org.eazyportal.documentstore.web.service.DocumentRetrieveFacade
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.springframework.data.domain.PageImpl

@ExtendWith(MockitoExtension::class)
class StoreDocumentRetrieveControllerTest {

    @Mock
    private lateinit var documentRetrieveFacade: DocumentRetrieveFacade

    @Mock
    private lateinit var parameterParser: ParameterParser

    @InjectMocks
    private lateinit var storeDocumentRetrieveController: StoreDocumentRetrieveController

    @Test
    fun `test getMemberDocuments`() {
        val requestParameters = mapOf("query.filter1" to "value1")
        val filterOptions = mapOf("filter1" to "value1")
        val storedDocuments = listOf(getStoredDocument())
        val allPagedDocument = PageImpl(storedDocuments, DEFAULT_PAGEABLE, 1)
        whenever(parameterParser.getFilterOptions(requestParameters)).thenReturn(filterOptions)
        whenever(documentRetrieveFacade.getAllDocuments(MEMBER_ID, filterOptions, DEFAULT_PAGEABLE)).thenReturn(allPagedDocument)

        val result = storeDocumentRetrieveController.getMemberDocuments(MEMBER_ID, requestParameters, DEFAULT_PAGEABLE)

        verify(parameterParser).getFilterOptions(requestParameters)
        verify(documentRetrieveFacade).getAllDocuments(MEMBER_ID, filterOptions, DEFAULT_PAGEABLE)
        assertThat(result).isEqualTo(allPagedDocument)
    }
}
