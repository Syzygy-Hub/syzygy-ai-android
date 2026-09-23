package com.syzygy.ai.contracts.rag

data class RAGOptions(
    val scoreThreshold: Double? = null,
    val metadata: Map<String, String> = emptyMap(),
)
