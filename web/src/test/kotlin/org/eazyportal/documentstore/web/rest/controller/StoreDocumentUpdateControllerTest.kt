package org.eazyportal.documentstore.web.rest.controller

import org.assertj.core.api.Assertions.assertThat
import org.eazyportal.documentstore.CommonFixtureValues.DOCUMENT_ID_STRING
import org.eazyportal.documentstore.test.utils.ModelUtils.getDocumentUpdateRequest
import org.eazyportal.documentstore.test.utils.ModelUtils.getStoredDocument
import org.eazyportal.documentstore.web.service.DocumentUpdateFacade
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.whenever

@ExtendWith(MockitoExtension::class)
class StoreDocumentUpdateControllerTest {

    @Mock
    private lateinit var documentUpdateFacade: DocumentUpdateFacade

    @InjectMocks
    private lateinit var storeDocumentUpdateController: StoreDocumentUpdateController

    @Test
    fun `test updateDocument`() {
        val documentUpdateRequest = getDocumentUpdateRequest()
        val updatedStoredDocument = getStoredDocument()
        whenever(documentUpdateFacade.updateDocument(DOCUMENT_ID_STRING, documentUpdateRequest))
            .thenReturn(updatedStoredDocument)

        val result = storeDocumentUpdateController.updateDocument(DOCUMENT_ID_STRING, documentUpdateRequest)

        assertThat(result).isEqualTo(updatedStoredDocument)
    }
}
