package com.syzygy.ai.contracts.agents

import com.syzygy.ai.contracts.llm.TokenUsage

data class AgentResult(
    val finalAnswer: String,
    val steps: List<AgentStep> = emptyList(),
    val tokenUsage: TokenUsage? = null,
)
