package org.eazyportal.documentstore

import java.util.UUID

object CommonFixtureValues {

    const val DOCUMENT_NAME = "document-name"
    const val DOCUMENT_TYPE_NAME = "document-type"
    const val DOCUMENT_TYPE_ID = "6418a7917f9f210986d50ed2"
    val MEMBER_ID: UUID = UUID.fromString("0b1e0c6c-710b-4641-8530-ee9beb760db9")
    const val ORIGINAL_FILENAME = "original-filename.txt"
    const val SAVED_FILENAME = "saved-filename.txt"
    val TAX_RETURN_PERIOD_META = mapOf(
        "year" to "2023", "quarter" to "2", "month" to "APRIL"
    )
    val METADATA = mapOf("TaxReturnPeriod" to TAX_RETURN_PERIOD_META)

}
