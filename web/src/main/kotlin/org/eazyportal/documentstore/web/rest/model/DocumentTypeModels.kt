package org.eazyportal.documentstore.web.rest.model

data class DocumentTypeListResponse(val documentTypes: List<DocumentType>)

data class DocumentType(val name: String, val id: String? = null)

data class DocumentTypeCreateRequest(val name: String)
