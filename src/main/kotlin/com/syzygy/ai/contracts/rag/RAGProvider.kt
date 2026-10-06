package com.syzygy.ai.contracts.rag

interface RAGProvider {
    /**
     * Retrieves chunks relevant to [query]. The result size is bounded by [RAGOptions.maxResults].
     */
    suspend fun retrieve(
        query: String,
        options: RAGOptions = RAGOptions(),
    ): List<RAGChunk>
}
