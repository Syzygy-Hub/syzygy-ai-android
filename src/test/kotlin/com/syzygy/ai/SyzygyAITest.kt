package com.syzygy.ai

import kotlin.test.Test
import kotlin.test.assertTrue

class SyzygyAITest {
    @Test
    fun `VERSION is a semantic version`() {
        assertTrue(SyzygyAI.VERSION.isNotBlank())
        assertTrue(
            Regex("""^\d+\.\d+\.\d+$""").matches(SyzygyAI.VERSION),
            "SyzygyAI.VERSION must be MAJOR.MINOR.PATCH but was '${SyzygyAI.VERSION}'",
        )
    }
}
