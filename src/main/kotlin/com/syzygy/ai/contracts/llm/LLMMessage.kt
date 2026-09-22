package com.syzygy.ai.contracts.llm

data class LLMMessage(
    val role: Role,
    val content: String,
) {
    enum class Role { USER, ASSISTANT, SYSTEM, TOOL }
}
