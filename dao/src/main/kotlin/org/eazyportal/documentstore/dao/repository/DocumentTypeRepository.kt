package org.eazyportal.documentstore.dao.repository

import org.bson.types.ObjectId
import org.eazyportal.documentstore.dao.model.DocumentTypeEntity
import org.springframework.data.mongodb.repository.MongoRepository

interface DocumentTypeRepository : MongoRepository<DocumentTypeEntity, ObjectId>
