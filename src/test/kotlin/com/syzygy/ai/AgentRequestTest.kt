package com.syzygy.ai

import com.syzygy.ai.contracts.agents.AgentRequest
import kotlin.test.Test
import kotlin.test.assertEquals

class AgentRequestTest {
    @Test
    fun `maxSteps defaults to 10`() {
        assertEquals(10, AgentRequest(input = "hi").maxSteps)
    }

    @Test
    fun `maxSteps of zero is clamped to 1`() {
        assertEquals(1, AgentRequest(input = "hi", maxSteps = 0).maxSteps)
    }

    @Test
    fun `negative maxSteps is clamped to 1`() {
        assertEquals(1, AgentRequest(input = "hi", maxSteps = -5).maxSteps)
    }

    @Test
    fun `explicit maxSteps is preserved`() {
        assertEquals(25, AgentRequest(input = "hi", maxSteps = 25).maxSteps)
    }

    @Test
    fun `copy preserves fields and re-applies clamping`() {
        val original = AgentRequest(input = "hi", maxSteps = 25)
        assertEquals(25, original.copy(input = "other").maxSteps)
        assertEquals(1, original.copy(maxSteps = 0).maxSteps)
        assertEquals(original, original.copy())
    }
}
