package com.syzygy.ai.contracts.rag

data class RAGChunk(
    val id: String? = null,
    val content: String,
    val score: Double,
    val metadata: Map<String, String> = emptyMap(),
    val source: String? = null,
    val documentId: String? = null,
)
