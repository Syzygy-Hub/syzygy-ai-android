package com.syzygy.ai.contracts.agents

data class AgentStep(
    val action: String,
    val input: Map<String, Any>,
    val output: String,
    val metadata: Map<String, String> = emptyMap(),
)
