package org.eazyportal.documentstore.service.document

import org.eazyportal.documentstore.dao.model.DocumentStatus.PENDING
import org.eazyportal.documentstore.dao.model.StoredDocumentEntity
import org.eazyportal.documentstore.dao.repository.StoredDocumentRepository
import org.eazyportal.documentstore.service.document.model.Document
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.support.PageableExecutionUtils
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class DocumentService(
    private val storedDocumentRepository: StoredDocumentRepository, private val mongoTemplate: MongoTemplate
) {

    fun saveDocument(document: Document) {
        val storedDocument = StoredDocumentEntity(
            documentType = document.documentType,
            displayName = document.name,
            savedFilename = document.savedFilename,
            originalFilename = document.originalFilename,
            status = PENDING,
            owner = document.memberId,
            modifiedBy = document.memberId,
            metadata = document.metadata
        )

        storedDocumentRepository.save(storedDocument)
    }

    fun getAllDocuments(
        memberId: UUID, filterOptions: Map<String, String>, pageable: Pageable
    ): Page<StoredDocumentEntity> {
        val query = getQuery(memberId, filterOptions)
        val pagedDocuments = findPageOfStoredDocumentEntities(query, pageable)
        return PageableExecutionUtils.getPage(pagedDocuments, pageable) {
            mongoTemplate.count(query.limit(-1).skip(-1), StoredDocumentEntity::class.java)
        }
    }

    private fun findPageOfStoredDocumentEntities(
        query: Query, pageable: Pageable
    ): List<StoredDocumentEntity> = mongoTemplate.find(query.with(pageable), StoredDocumentEntity::class.java)

    private fun getQuery(memberId: UUID, filterOptions: Map<String, String>): Query {
        val filter = getCompleteFilter(memberId, filterOptions)
        return Query(filter)
    }

    private fun getCompleteFilter(memberId: UUID, filterOptions: Map<String, String>): Criteria {
        val criteria = getBaseFilter(memberId)
        return getFilterWithOptions(filterOptions, criteria)
    }

    private fun getBaseFilter(memberId: UUID) = Criteria.where("owner").`is`(memberId)

    private fun getFilterWithOptions(filterOptions: Map<String, String>, criteria: Criteria): Criteria =
        if (filterOptions.isNotEmpty()) {
            val criteriaList = filterOptions.map { entry -> Criteria.where(entry.key).`is`(entry.value) }
            criteria.andOperator(criteriaList)
        } else {
            criteria
        }

}
