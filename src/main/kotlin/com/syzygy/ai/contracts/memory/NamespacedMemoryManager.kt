package com.syzygy.ai.contracts.memory

interface NamespacedMemoryManager : MemoryManager {
    suspend fun add(
        entry: MemoryEntry,
        namespace: String,
    )

    suspend fun retrieve(
        query: String,
        namespace: String,
        limit: Int? = null,
    ): List<MemoryEntry>

    suspend fun delete(
        id: String,
        namespace: String,
    )

    suspend fun clear(namespace: String)
}
