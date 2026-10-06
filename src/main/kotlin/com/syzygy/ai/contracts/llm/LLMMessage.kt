package com.syzygy.ai.contracts.llm

data class LLMMessage(
    val role: Role,
    val content: String,
    val toolCalls: List<ToolCall>? = null,
    val toolCallResult: ToolCallResult? = null,
) {
    enum class Role { USER, ASSISTANT, SYSTEM, TOOL }
}
