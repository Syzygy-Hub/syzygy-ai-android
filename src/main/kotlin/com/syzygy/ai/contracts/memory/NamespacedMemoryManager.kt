package com.syzygy.ai.contracts.memory

interface NamespacedMemoryManager : MemoryManager {
    suspend fun addToNamespace(
        entry: MemoryEntry,
        namespace: String,
    )

    suspend fun retrieveFromNamespace(
        query: String,
        namespace: String,
        limit: Int? = null,
    ): List<MemoryEntry>

    suspend fun deleteEntry(
        id: String,
        namespace: String,
    )

    suspend fun clearNamespace(namespace: String)
}
