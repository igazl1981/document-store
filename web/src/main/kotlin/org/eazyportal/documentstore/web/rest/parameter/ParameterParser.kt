package org.eazyportal.documentstore.web.rest.parameter

import org.springframework.stereotype.Service

@Service
class ParameterParser {

    fun getFilterOptions(requestParameters: Map<String, String>): Map<String, String> {
        return requestParameters.mapNotNull(::getFilterOption).toMap()
    }

    private fun getFilterOption(entry: Map.Entry<String, String>) = if (isQueryParam(entry) && valueIsNotEmpty(entry)) {
        val field = getFieldName(entry)
        field to entry.value
    } else null

    private fun isQueryParam(entry: Map.Entry<String, String>) = entry.key.startsWith("query.")

    private fun valueIsNotEmpty(entry: Map.Entry<String, String>) = entry.value.isNotBlank()

    private fun getFieldName(entry: Map.Entry<String, String>) = entry.key.substring(6)
}
