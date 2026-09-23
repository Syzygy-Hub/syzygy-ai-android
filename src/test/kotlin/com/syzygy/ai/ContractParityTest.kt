package com.syzygy.ai

import com.syzygy.ai.contracts.AIError
import com.syzygy.ai.contracts.llm.LLMProvider
import com.syzygy.ai.contracts.llm.LLMRequest
import com.syzygy.ai.contracts.llm.ToolCallRequest
import com.syzygy.ai.contracts.llm.ToolCallResult
import com.syzygy.ai.contracts.rag.RAGChunk
import com.syzygy.ai.contracts.rag.RAGOptions
import com.syzygy.ai.contracts.rag.RAGProvider
import com.syzygy.ai.types.JSONValue
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

/**
 * Compile-time contract parity tests for Syzygy AI Layer v1.1.0.
 *
 * These tests do not execute production logic — they verify that all required
 * contracts exist with the expected shapes by constructing instances and
 * referencing interfaces. A compilation failure here means a contract regressed.
 */
class ContractParityTest {

    @Test
    fun `LLMProvider interface has complete and stream methods`() {
        // Verify via reflection that the interface declares both methods.
        val methods = LLMProvider::class.java.declaredMethods.map { it.name }
        assertTrue("complete" in methods, "LLMProvider must have a 'complete' method")
        assertTrue("stream" in methods, "LLMProvider must have a 'stream' method")
    }

    @Test
    fun `JSONValue sealed class hierarchy compiles and all variants are reachable`() {
        val values: List<JSONValue> = listOf(
            JSONValue.Null,
            JSONValue.Bool(true),
            JSONValue.Number(3.14),
            JSONValue.StringValue("hello"),
            JSONValue.Array(emptyList()),
            JSONValue.Object(emptyMap()),
        )
        assertTrue(values.size == 6)
    }

    @Test
    fun `AIError sealed class hierarchy compiles and all variants are reachable`() {
        val errors: List<AIError> = listOf(
            AIError.AuthenticationFailure("bad key"),
            AIError.RateLimited(retryAfterMs = 1000L),
            AIError.NetworkError(RuntimeException("timeout")),
            AIError.InvalidRequest("missing field"),
            AIError.ProviderFailure("5xx"),
            AIError.Cancelled,
        )
        assertTrue(errors.size == 6)
    }

    @Test
    fun `ToolCallRequest and ToolCallResult exist and construct correctly`() {
        val request = ToolCallRequest(
            id = "call_001",
            name = "search",
            arguments = mapOf("query" to JSONValue.StringValue("kotlin")),
        )
        val result = ToolCallResult(
            toolCallId = "call_001",
            content = "results...",
            isError = false,
        )
        assertNotNull(request)
        assertNotNull(result)
    }

    @Test
    fun `RAGChunk has required id field`() {
        val chunk = RAGChunk(
            id = "chunk-1",
            content = "some text",
            score = 0.95,
        )
        assertTrue(chunk.id == "chunk-1")
    }

    @Test
    fun `RAGProvider retrieve accepts query topK and options`() {
        val provider = object : RAGProvider {
            override suspend fun retrieve(query: String, topK: Int, options: RAGOptions?): List<RAGChunk> = emptyList()
        }
        // Compile-time check: signature matches
        assert(true)
    }

    @Test
    fun `LLMRequest has requestId and correlationId fields`() {
        val request = LLMRequest(
            messages = emptyList(),
            model = "test-model",
            requestId = "req-123",
            correlationId = "corr-456",
        )
        assertTrue(request.requestId == "req-123")
        assertTrue(request.correlationId == "corr-456")
    }
}
