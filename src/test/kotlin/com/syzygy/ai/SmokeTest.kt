package com.syzygy.ai

import com.syzygy.ai.contracts.agents.AgentResult
import com.syzygy.ai.contracts.embeddings.Embedding
import com.syzygy.ai.contracts.llm.LLMMessage
import com.syzygy.ai.contracts.llm.LLMRequest
import com.syzygy.ai.contracts.memory.MemoryEntry
import com.syzygy.ai.contracts.rag.RAGChunk
import com.syzygyhub.foundation.primitives.time.SyzygyTimestamp
import kotlin.test.Test
import kotlin.test.assertEquals

class SmokeTest {
    @Test
    fun llmRequestIsConstructible() {
        val req =
            LLMRequest(
                messages = listOf(LLMMessage(LLMMessage.Role.USER, "hi")),
                model = "test",
            )
        assertEquals("test", req.model)
    }

    @Test
    fun embeddingHasDimensions() {
        val emb = Embedding(floatArrayOf(0.1f, 0.2f), dimensions = 2)
        assertEquals(2, emb.dimensions)
    }

    @Test
    fun ragChunkHasScore() {
        val chunk = RAGChunk(content = "test", score = 0.9)
        assertEquals(0.9, chunk.score, 0.001)
    }

    @Test
    fun agentResultHasAnswer() {
        val result = AgentResult(finalAnswer = "42")
        assertEquals("42", result.finalAnswer)
    }

    @Test
    fun memoryEntryHasType() {
        val entry =
            MemoryEntry(
                id = "1",
                content = "fact",
                timestamp = SyzygyTimestamp.now(),
                type = "fact",
            )
        assertEquals("fact", entry.type)
    }
}
