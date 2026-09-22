package com.syzygy.ai.contracts.embeddings

data class Embedding(
    val values: FloatArray,
    val dimensions: Int,
    val metadata: Map<String, String> = emptyMap(),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Embedding) return false
        return values.contentEquals(other.values) &&
            dimensions == other.dimensions &&
            metadata == other.metadata
    }

    override fun hashCode(): Int {
        var result = values.contentHashCode()
        result = 31 * result + dimensions
        result = 31 * result + metadata.hashCode()
        return result
    }
}
