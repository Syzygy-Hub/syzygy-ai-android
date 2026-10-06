package com.syzygy.ai.contracts.llm

import com.syzygy.ai.types.JSONObject

/**
 * A tool invocation requested by the model in an [LLMResponse].
 *
 * @property id Provider-assigned identifier used to correlate the result.
 * @property name Name of the tool to invoke.
 * @property arguments Arguments for the tool as a JSON object.
 */
data class ToolCall(
    val id: String,
    val name: String,
    val arguments: JSONObject,
)
