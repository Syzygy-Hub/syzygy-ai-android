package com.syzygy.ai.contracts.llm

data class TokenUsage(
    val promptTokens: Int,
    val completionTokens: Int,
    val totalTokens: Int,
)

enum class FinishReason { STOP, LENGTH, TOOL_CALL, CONTENT_FILTER, ERROR }

/**
 * Result of an LLM call.
 *
 * @property toolCalls Tool invocations requested by the model, or `null` when none.
 */
data class LLMResponse(
    val content: String,
    val tokenUsage: TokenUsage? = null,
    val finishReason: FinishReason? = null,
    val providerName: String? = null,
    val modelName: String? = null,
    val toolCalls: List<ToolCall>? = null,
)
