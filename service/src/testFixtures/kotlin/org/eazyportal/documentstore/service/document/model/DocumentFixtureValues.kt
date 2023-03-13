package org.eazyportal.documentstore.service.document.model

import org.eazyportal.documentstore.CommonFixtureValues.DOCUMENT_NAME
import org.eazyportal.documentstore.CommonFixtureValues.DOCUMENT_TYPE
import org.eazyportal.documentstore.CommonFixtureValues.MEMBER_ID
import org.eazyportal.documentstore.CommonFixtureValues.METADATA
import org.eazyportal.documentstore.CommonFixtureValues.ORIGINAL_FILENAME
import org.eazyportal.documentstore.CommonFixtureValues.SAVED_FILENAME

object DocumentFixtureValues {

    val DOCUMENT = Document(
        memberId = MEMBER_ID,
        name = DOCUMENT_NAME,
        documentType = DOCUMENT_TYPE,
        savedFilename = SAVED_FILENAME,
        originalFilename = ORIGINAL_FILENAME,
        metadata = METADATA
    )

}
