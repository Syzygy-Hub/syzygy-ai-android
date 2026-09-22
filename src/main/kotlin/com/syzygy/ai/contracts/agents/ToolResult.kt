package com.syzygy.ai.contracts.agents

data class ToolResult(
    val output: String,
    val isError: Boolean = false,
    val metadata: Map<String, String> = emptyMap(),
)
