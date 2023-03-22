package org.eazyportal.documentstore.dao.repository

import org.bson.types.ObjectId
import org.eazyportal.documentstore.dao.model.StoredDocumentEntity
import org.springframework.data.mongodb.repository.MongoRepository

interface StoredDocumentRepository : MongoRepository<StoredDocumentEntity, ObjectId>
