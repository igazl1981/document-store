package org.eazyportal.documentstore.web.service

import org.eazyportal.documentstore.service.document.DocumentService
import org.eazyportal.documentstore.service.document.DocumentTypeService
import org.eazyportal.documentstore.service.document.exception.DocumentTypeHasAssignedDocumentsException
import org.eazyportal.documentstore.service.document.exception.MissingDocumentTypeException
import org.eazyportal.documentstore.service.document.model.Type
import org.eazyportal.documentstore.web.rest.model.DocumentType
import org.eazyportal.documentstore.web.rest.model.DocumentTypeCreateRequest
import org.eazyportal.documentstore.web.service.transformer.DocumentTypeTransformer
import org.springframework.stereotype.Service

@Service
class DocumentTypeFacade(
    private val documentService: DocumentService,
    private val documentTypeService: DocumentTypeService, private val documentTypeTransformer: DocumentTypeTransformer
) {

    fun getAll(): List<DocumentType> = documentTypeService.getAll().map(documentTypeTransformer::toDto)

    fun getById(id: String): DocumentType? = documentTypeService.getById(id)?.let(documentTypeTransformer::toDto)

    fun add(documentTypeCreateRequest: DocumentTypeCreateRequest): DocumentType {
        return documentTypeService.add(createType(documentTypeCreateRequest)).let(documentTypeTransformer::toDto)
    }

    fun update(id: String, documentTypeCreateRequest: DocumentTypeCreateRequest): DocumentType =
        documentTypeService.update(id, createType(documentTypeCreateRequest)).let(documentTypeTransformer::toDto)

    fun delete(documentTypeId: String) {
        val documentType = documentTypeService.getById(documentTypeId) ?: throw MissingDocumentTypeException(documentTypeId)
        documentType
            .takeIf { documentService.countByType(it) == 0L }
            ?.also { documentTypeService.delete(it) }
            ?: throw DocumentTypeHasAssignedDocumentsException(documentTypeId, documentType.name)
    }

    private fun createType(documentTypeCreateRequest: DocumentTypeCreateRequest) = Type(documentTypeCreateRequest.name)
}
