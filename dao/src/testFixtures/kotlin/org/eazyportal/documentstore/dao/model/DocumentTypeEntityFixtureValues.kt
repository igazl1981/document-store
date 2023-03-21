package org.eazyportal.documentstore.dao.model

import org.bson.types.ObjectId
import org.eazyportal.documentstore.CommonFixtureValues.DOCUMENT_TYPE_ID
import org.eazyportal.documentstore.CommonFixtureValues.DOCUMENT_TYPE_NAME

object DocumentTypeEntityFixtureValues {

    val DOCUMENT_TYPE = DocumentTypeEntity(DOCUMENT_TYPE_NAME, ObjectId(DOCUMENT_TYPE_ID))

}
