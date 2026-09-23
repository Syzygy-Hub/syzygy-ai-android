package com.syzygy.ai.contracts.llm

data class LLMRequest(
    val messages: List<LLMMessage>,
    val model: String,
    val temperature: Double? = null,
    val maxTokens: Int? = null,
    val topP: Double? = null,
    val stopSequences: List<String> = emptyList(),
    val requestId: String? = null,
    val correlationId: String? = null,
)
