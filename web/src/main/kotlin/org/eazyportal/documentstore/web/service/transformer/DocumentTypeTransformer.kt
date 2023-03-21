package org.eazyportal.documentstore.web.service.transformer

import org.eazyportal.documentstore.dao.model.DocumentTypeEntity
import org.springframework.stereotype.Component
import org.eazyportal.documentstore.web.rest.model.DocumentType as DocumentTypeDto

@Component
class DocumentTypeTransformer {

    fun toDto(entity: DocumentTypeEntity): DocumentTypeDto = DocumentTypeDto(entity.name, entity.id.toString())
}
