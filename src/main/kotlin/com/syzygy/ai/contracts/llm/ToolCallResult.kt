package com.syzygy.ai.contracts.llm

data class ToolCallResult(
    val toolCallId: String,
    val content: String,
    val isError: Boolean = false,
)
