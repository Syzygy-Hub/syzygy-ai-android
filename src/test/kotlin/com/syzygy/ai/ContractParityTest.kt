package com.syzygy.ai

import com.syzygy.ai.contracts.AIError
import com.syzygy.ai.contracts.agents.AgentTool
import com.syzygy.ai.contracts.agents.ToolResult
import com.syzygy.ai.contracts.llm.LLMProvider
import com.syzygy.ai.contracts.llm.LLMRequest
import com.syzygy.ai.contracts.llm.LLMResponse
import com.syzygy.ai.contracts.llm.ToolCall
import com.syzygy.ai.contracts.llm.ToolCallResult
import com.syzygy.ai.contracts.memory.MemoryEntry
import com.syzygy.ai.contracts.memory.NamespacedMemoryManager
import com.syzygy.ai.contracts.rag.RAGChunk
import com.syzygy.ai.contracts.rag.RAGOptions
import com.syzygy.ai.contracts.rag.RAGProvider
import com.syzygy.ai.types.JSONValue
import com.syzygyhub.foundation.primitives.time.SyzygyTimestamp
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

/**
 * Contract parity tests for the Syzygy AI Layer.
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
        val values: List<JSONValue> =
            listOf(
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
        val errors: List<AIError> =
            listOf(
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
    fun `ToolCall and ToolCallResult exist and construct correctly`() {
        val request =
            ToolCall(
                id = "call_001",
                name = "search",
                arguments = mapOf("query" to JSONValue.StringValue("kotlin")),
            )
        val result =
            ToolCallResult(
                toolCallId = "call_001",
                content = "results...",
                isError = false,
            )
        assertNotNull(request)
        assertNotNull(result)
    }

    @Test
    fun `RAGChunk id is optional and defaults to null`() {
        val withId = RAGChunk(id = "chunk-1", content = "some text", score = 0.95)
        val withoutId = RAGChunk(content = "some text", score = 0.95)
        assertTrue(withId.id == "chunk-1")
        assertNull(withoutId.id)
    }

    @Test
    fun `RAGProvider retrieve accepts query and options with maxResults`() {
        val expected = RAGChunk(content = "hit", score = 0.5)
        var seenQuery: String? = null
        var seenOptions: RAGOptions? = null
        val provider =
            object : RAGProvider {
                override suspend fun retrieve(
                    query: String,
                    options: RAGOptions,
                ): List<RAGChunk> {
                    seenQuery = query
                    seenOptions = options
                    return listOf(expected)
                }
            }
        val options = RAGOptions(scoreThreshold = 0.1, maxResults = 3)
        val result = runBlocking { provider.retrieve("q", options) }
        assertEquals(listOf(expected), result)
        assertEquals("q", seenQuery)
        assertEquals(3, seenOptions?.maxResults)
        assertEquals(options, seenOptions)
    }

    @Test
    fun `RAGProvider retrieve uses default options when omitted`() {
        var seenOptions: RAGOptions? = null
        val provider =
            object : RAGProvider {
                override suspend fun retrieve(
                    query: String,
                    options: RAGOptions,
                ): List<RAGChunk> {
                    seenOptions = options
                    return emptyList()
                }
            }
        runBlocking { provider.retrieve("q") }
        assertEquals(10, seenOptions?.maxResults)
    }

    @Test
    fun `RAGOptions maxResults defaults to 10 and clamps to at least 1`() {
        assertEquals(10, RAGOptions().maxResults)
        assertEquals(1, RAGOptions(maxResults = 0).maxResults)
        assertEquals(1, RAGOptions(maxResults = -5).maxResults)
        assertEquals(25, RAGOptions(maxResults = 25).maxResults)
        assertEquals(1, RAGOptions().copy(maxResults = 0).maxResults)
    }

    @Test
    fun `ToolCall constructs and compares by value`() {
        val args = mapOf("q" to JSONValue.StringValue("x"))
        val a = ToolCall(id = "1", name = "search", arguments = args)
        assertEquals(ToolCall("1", "search", args), a)
        assertEquals("search", a.name)
    }

    @Test
    fun `LLMRequest tools default null and accepts tools`() {
        assertNull(LLMRequest(messages = emptyList(), model = "m").tools)
        val tool = AgentTool("t", "d", emptyMap()) { ToolResult(output = "ok") }
        val request = LLMRequest(messages = emptyList(), model = "m", tools = listOf(tool))
        assertEquals(listOf(tool), request.tools)
    }

    @Test
    fun `LLMResponse toolCalls default null and accepts toolCalls`() {
        assertNull(LLMResponse(content = "hi").toolCalls)
        val call = ToolCall("1", "search", emptyMap())
        val response = LLMResponse(content = "", toolCalls = listOf(call))
        assertEquals(listOf(call), response.toolCalls)
    }

    @Test
    fun `NamespacedMemoryManager exposes aligned method names`() {
        val calls = mutableListOf<String>()
        val manager =
            object : NamespacedMemoryManager {
                override suspend fun addToNamespace(
                    entry: MemoryEntry,
                    namespace: String,
                ) {
                    calls += "add:$namespace"
                }

                override suspend fun retrieveFromNamespace(
                    query: String,
                    namespace: String,
                    limit: Int?,
                ): List<MemoryEntry> {
                    calls += "retrieve:$namespace"
                    return emptyList()
                }

                override suspend fun deleteEntry(
                    id: String,
                    namespace: String,
                ) {
                    calls += "delete:$id@$namespace"
                }

                override suspend fun clearNamespace(namespace: String) {
                    calls += "clear:$namespace"
                }

                override suspend fun add(entry: MemoryEntry) = Unit

                override suspend fun retrieve(
                    query: String,
                    limit: Int,
                ): List<MemoryEntry> = emptyList()

                override suspend fun clear() = Unit
            }
        runBlocking {
            manager.addToNamespace(MemoryEntry(id = "e", content = "c", timestamp = SyzygyTimestamp.now(), type = "note"), "ns")
            manager.retrieveFromNamespace("q", "ns")
            manager.deleteEntry("e", "ns")
            manager.clearNamespace("ns")
        }
        assertEquals(listOf("add:ns", "retrieve:ns", "delete:e@ns", "clear:ns"), calls)
    }

    @Test
    fun `LLMRequest has requestId and correlationId fields`() {
        val request =
            LLMRequest(
                messages = emptyList(),
                model = "test-model",
                requestId = "req-123",
                correlationId = "corr-456",
            )
        assertTrue(request.requestId == "req-123")
        assertTrue(request.correlationId == "corr-456")
    }
}
