package org.eazyportal.documentstore.dao

import org.springframework.data.mongodb.repository.MongoRepository

interface StoredDocumentRepository : MongoRepository<StoredDocument, String> {
}
