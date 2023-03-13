package org.eazyportal.documentstore.web.rest.parameter

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ParameterParserTest {

    private val parameterParser = ParameterParser()

    @Test
    fun `test getFilterOptions should return filter options when start with query and has not blank value`(){
        val requestParameters = mapOf(
            "non-filter-option" to "some-value",
            "query.empty.option.value" to "",
            "query.spaces.only.option.value" to "     ",
            "query.valid.not-blank.value" to "valid-value"
        )

        val result = parameterParser.getFilterOptions(requestParameters)

        val expected = mapOf(
            "valid.not-blank.value" to "valid-value"
        )
        assertThat(result).isEqualTo(expected)
    }
}
