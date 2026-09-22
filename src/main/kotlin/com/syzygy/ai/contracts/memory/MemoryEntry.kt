package com.syzygy.ai.contracts.memory

import com.syzygyhub.foundation.primitives.time.SyzygyTimestamp

data class MemoryEntry(
    val id: String,
    val content: String,
    val metadata: Map<String, String> = emptyMap(),
    val timestamp: SyzygyTimestamp,
    val type: String,
)
