package org.eazyportal.documentstore.service.document

import org.bson.types.ObjectId
import org.eazyportal.documentstore.dao.model.DocumentTypeEntity
import org.eazyportal.documentstore.dao.repository.DocumentTypeRepository
import org.eazyportal.documentstore.service.document.exception.DocumentTypeNotFoundException
import org.eazyportal.documentstore.service.document.model.Type
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DocumentTypeService(private val documentTypeRepository: DocumentTypeRepository) {

    fun getAll(): List<DocumentTypeEntity> = documentTypeRepository.findAll()

    fun getById(id: String): DocumentTypeEntity? = documentTypeRepository.findById(ObjectId(id)).orElse(null)

    @Transactional
    fun add(type: Type): DocumentTypeEntity {
        return insert(type)
    }

    @Transactional
    fun update(id: String, type: Type): DocumentTypeEntity {
        return getById(id)?.let { update(it, type) } ?: throw DocumentTypeNotFoundException(id)
    }

    private fun insert(type: Type): DocumentTypeEntity {
        val newEntity = DocumentTypeEntity(type.name)
        return documentTypeRepository.insert(newEntity)
    }

    private fun update(entity: DocumentTypeEntity, type: Type): DocumentTypeEntity {
        entity.name = type.name
        return documentTypeRepository.save(entity)
    }

}
