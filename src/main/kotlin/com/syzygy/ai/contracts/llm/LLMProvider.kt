package com.syzygy.ai.contracts.llm

import kotlinx.coroutines.flow.Flow

interface LLMProvider {
    suspend fun complete(request: LLMRequest): LLMResponse

    fun stream(request: LLMRequest): Flow<LLMChunk>
}
