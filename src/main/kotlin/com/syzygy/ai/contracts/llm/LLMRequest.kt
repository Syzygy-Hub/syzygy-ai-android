package com.syzygy.ai.contracts.llm

import com.syzygy.ai.contracts.agents.AgentTool

/**
 * Input to an LLM call.
 *
 * @property tools Tools the model may call, or `null` for none. [AgentTool] holds a lambda, so
 *   equality of requests with tools is reference-based for the tool's `execute` function.
 */
data class LLMRequest(
    val messages: List<LLMMessage>,
    val model: String,
    val temperature: Double? = null,
    val maxTokens: Int? = null,
    val topP: Double? = null,
    val stopSequences: List<String> = emptyList(),
    val requestId: String? = null,
    val correlationId: String? = null,
    val tools: List<AgentTool>? = null,
)
