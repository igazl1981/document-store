package org.eazyportal.documentstore.web.rest.controller

import org.eazyportal.documentstore.web.rest.model.DocumentType
import org.eazyportal.documentstore.web.rest.model.DocumentTypeCreateRequest
import org.eazyportal.documentstore.web.rest.model.DocumentTypeListResponse
import org.eazyportal.documentstore.web.service.DocumentTypeFacade
import org.springframework.http.HttpStatus.CREATED
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/documents/types")
class DocumentTypeController(private val documentTypeFacade: DocumentTypeFacade) {


    @GetMapping
    fun getAll(): DocumentTypeListResponse {
        return DocumentTypeListResponse(documentTypeFacade.getAll())
    }


    @GetMapping("/{id}")
    fun getById(@PathVariable id: String): DocumentType? {
        return documentTypeFacade.getById(id)
    }

    @PostMapping
    @ResponseStatus(CREATED)
    fun add(@RequestBody documentTypeCreateRequest: DocumentTypeCreateRequest): DocumentType {
        return documentTypeFacade.add(documentTypeCreateRequest)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: String, @RequestBody documentTypeCreateRequest: DocumentTypeCreateRequest): DocumentType {
        return documentTypeFacade.update(id, documentTypeCreateRequest)
    }

}
