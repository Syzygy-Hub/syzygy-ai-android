package com.syzygy.ai.contracts.llm

data class LLMChunk(
    val content: String? = null,
    val toolCallDelta: String? = null,
    val finishReason: FinishReason? = null,
    val metadata: Map<String, String> = emptyMap(),
    val providerName: String? = null,
    val modelName: String? = null,
)
