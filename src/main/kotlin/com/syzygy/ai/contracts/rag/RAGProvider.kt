package com.syzygy.ai.contracts.rag

data class RAGOptions(
    val scoreThreshold: Double? = null,
    val metadata: Map<String, String> = emptyMap(),
)

interface RAGProvider {
    suspend fun retrieve(
        query: String,
        topK: Int,
        options: RAGOptions? = null,
    ): List<RAGChunk>
}
