package org.eazyportal.documentstore.test.utils

import org.eazyportal.documentstore.web.rest.controller.DocumentMetadata
import org.eazyportal.documentstore.web.rest.controller.DocumentUploadRequest
import org.eazyportal.documentstore.web.rest.controller.TaxReturnPeriod
import java.time.Month
import java.util.UUID

object ModelUtils {

    const val documentType = "document-type"
    val memberId: UUID = UUID.fromString("0b1e0c6c-710b-4641-8530-ee9beb760db9")

    fun getDocumentUploadRequest() = DocumentUploadRequest(
        memberId = memberId,
        name = "mock-uploaded-file.txt",
        documentType = documentType,
        metadata = getDocumentMetadata()
    )

    private fun getDocumentMetadata() = DocumentMetadata(
        taxReturnPeriod = getTaxReturnPeriod()
    )

    private fun getTaxReturnPeriod() = TaxReturnPeriod(
        year = 2023,
        quarter = 2,
        month = Month.APRIL
    )

}
