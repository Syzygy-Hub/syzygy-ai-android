package com.syzygy.ai.contracts.rag

data class RAGChunk(
    val content: String,
    val score: Double,
    val metadata: Map<String, String> = emptyMap(),
)
