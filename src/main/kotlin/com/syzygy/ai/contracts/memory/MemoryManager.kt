package com.syzygy.ai.contracts.memory

interface MemoryManager {
    suspend fun add(entry: MemoryEntry)

    suspend fun retrieve(
        query: String,
        limit: Int,
    ): List<MemoryEntry>

    suspend fun clear()
}
