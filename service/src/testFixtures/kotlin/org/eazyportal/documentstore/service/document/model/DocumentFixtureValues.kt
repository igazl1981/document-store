package org.eazyportal.documentstore.service.document.model

import org.eazyportal.documentstore.CommonFixtureValues.DOCUMENT_NAME
import org.eazyportal.documentstore.CommonFixtureValues.DOCUMENT_TYPE_NAME
import org.eazyportal.documentstore.CommonFixtureValues.MEMBER_ID
import org.eazyportal.documentstore.CommonFixtureValues.METADATA
import org.eazyportal.documentstore.CommonFixtureValues.ORIGINAL_FILENAME
import org.eazyportal.documentstore.CommonFixtureValues.SAVED_FILENAME

object DocumentFixtureValues {

    const val DOCUMENT_TYPE_UPDATED_NAME = "updated-document-name"

    val DOCUMENT = Document(
        memberId = MEMBER_ID,
        name = DOCUMENT_NAME,
        documentTypeId = DOCUMENT_TYPE_NAME,
        savedFilename = SAVED_FILENAME,
        originalFilename = ORIGINAL_FILENAME,
        metadata = METADATA
    )

    val TYPE_INSERT = Type(DOCUMENT_TYPE_NAME)

    val TYPE_UPDATE = Type(DOCUMENT_TYPE_UPDATED_NAME)

}
