package com.syzygy.ai.contracts.memory

import com.syzygyhub.foundation.primitives.time.SyzygyTimestamp

data class ConversationTurn(
    val role: Role,
    val content: String,
    val timestamp: SyzygyTimestamp,
    val metadata: Map<String, String> = emptyMap(),
) {
    enum class Role { USER, ASSISTANT, SYSTEM, TOOL }
}
