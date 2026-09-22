package com.syzygy.ai.contracts.embeddings

interface EmbeddingProvider {
    suspend fun embed(text: String): Embedding
}
