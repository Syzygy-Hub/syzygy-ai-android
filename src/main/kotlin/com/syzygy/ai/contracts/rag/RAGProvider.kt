package com.syzygy.ai.contracts.rag

interface RAGProvider {
    suspend fun retrieve(
        query: String,
        topK: Int,
        options: RAGOptions? = null,
    ): List<RAGChunk>
}
