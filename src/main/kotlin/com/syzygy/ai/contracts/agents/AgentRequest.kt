package com.syzygy.ai.contracts.agents

/**
 * Input to an agent run.
 *
 * @property input The user input the agent should act on.
 * @property tools Tools available to the agent.
 * @property maxSteps Maximum number of agent steps before the run is terminated. Defaults to 10.
 *   Exceeding it yields a truncated response. Values below 1 are clamped to 1.
 * @property metadata Arbitrary string metadata.
 */
class AgentRequest(
    val input: String,
    val tools: List<AgentTool> = emptyList(),
    maxSteps: Int = DEFAULT_MAX_STEPS,
    val metadata: Map<String, String> = emptyMap(),
) {
    val maxSteps: Int = maxSteps.coerceAtLeast(1)

    fun copy(
        input: String = this.input,
        tools: List<AgentTool> = this.tools,
        maxSteps: Int = this.maxSteps,
        metadata: Map<String, String> = this.metadata,
    ): AgentRequest = AgentRequest(input, tools, maxSteps, metadata)

    override fun equals(other: Any?): Boolean =
        this === other ||
            (
                other is AgentRequest &&
                    input == other.input &&
                    tools == other.tools &&
                    maxSteps == other.maxSteps &&
                    metadata == other.metadata
            )

    override fun hashCode(): Int {
        var result = input.hashCode()
        result = 31 * result + tools.hashCode()
        result = 31 * result + maxSteps
        result = 31 * result + metadata.hashCode()
        return result
    }

    override fun toString(): String = "AgentRequest(input=$input, tools=$tools, maxSteps=$maxSteps, metadata=$metadata)"

    private companion object {
        const val DEFAULT_MAX_STEPS = 10
    }
}
