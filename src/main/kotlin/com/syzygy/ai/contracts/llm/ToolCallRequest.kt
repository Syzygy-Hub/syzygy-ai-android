package com.syzygy.ai.contracts.llm

import com.syzygy.ai.types.JSONObject

data class ToolCallRequest(
    val id: String,
    val name: String,
    val arguments: JSONObject,
)
