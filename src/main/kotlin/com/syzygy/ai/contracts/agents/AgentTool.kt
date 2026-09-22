package com.syzygy.ai.contracts.agents

typealias ToolSchema = Map<String, Any>

typealias ToolInput = Map<String, Any>

data class AgentTool(
    val name: String,
    val description: String,
    val inputSchema: ToolSchema,
    val execute: suspend (ToolInput) -> ToolResult,
)
