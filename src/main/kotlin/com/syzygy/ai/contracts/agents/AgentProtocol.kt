package com.syzygy.ai.contracts.agents

interface AgentProtocol {
    suspend fun run(request: AgentRequest): AgentResult
}
