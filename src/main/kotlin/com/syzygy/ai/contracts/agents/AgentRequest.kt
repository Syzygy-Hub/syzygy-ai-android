package com.syzygy.ai.contracts.agents

data class AgentRequest(
    val input: String,
    val tools: List<AgentTool> = emptyList(),
    // v1.0.0 policy default: 10
    val maxSteps: Int = 10,
    val metadata: Map<String, String> = emptyMap(),
)
