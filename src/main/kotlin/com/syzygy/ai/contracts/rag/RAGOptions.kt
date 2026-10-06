package com.syzygy.ai.contracts.rag

/**
 * Options controlling a [RAGProvider.retrieve] call.
 *
 * @property scoreThreshold Minimum relevance score a chunk must have to be returned.
 * @property metadata Arbitrary string metadata filters.
 * @property maxResults Maximum number of chunks to return. Defaults to 10. Values below 1 are clamped to 1.
 */
class RAGOptions(
    val scoreThreshold: Double? = null,
    val metadata: Map<String, String> = emptyMap(),
    maxResults: Int = DEFAULT_MAX_RESULTS,
) {
    val maxResults: Int = maxResults.coerceAtLeast(1)

    fun copy(
        scoreThreshold: Double? = this.scoreThreshold,
        metadata: Map<String, String> = this.metadata,
        maxResults: Int = this.maxResults,
    ): RAGOptions = RAGOptions(scoreThreshold, metadata, maxResults)

    override fun equals(other: Any?): Boolean =
        this === other ||
            (
                other is RAGOptions &&
                    scoreThreshold == other.scoreThreshold &&
                    metadata == other.metadata &&
                    maxResults == other.maxResults
            )

    override fun hashCode(): Int {
        var result = scoreThreshold?.hashCode() ?: 0
        result = 31 * result + metadata.hashCode()
        result = 31 * result + maxResults
        return result
    }

    override fun toString(): String =
        "RAGOptions(scoreThreshold=$scoreThreshold, metadata=$metadata, maxResults=$maxResults)"

    private companion object {
        const val DEFAULT_MAX_RESULTS = 10
    }
}
