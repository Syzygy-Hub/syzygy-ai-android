package com.syzygy.ai.contracts.llm

import com.syzygy.ai.contracts.AIError
import kotlinx.coroutines.flow.Flow

/**
 * Stream semantics for [LLMProvider.stream].
 *
 * ## Completion
 * The [Flow] completes normally after emitting an [LLMChunk] whose [LLMChunk.finishReason]
 * is non-null (e.g. [FinishReason.STOP] or [FinishReason.LENGTH]). Collectors must not
 * assume any further chunks after that emission.
 *
 * ## Cancellation
 * Cancel the coroutine scope (or call [kotlinx.coroutines.Job.cancel]) collecting the Flow.
 * Providers must honour cooperative cancellation and release resources promptly.
 *
 * ## Partial Results
 * Every emitted [LLMChunk] is a valid, independently usable partial result. Concatenating
 * [LLMChunk.content] values in emission order reconstructs the full response text.
 *
 * ## Retry
 * On [AIError.NetworkError], callers may retry by invoking [LLMProvider.stream] again with
 * the same [LLMRequest], preserving the original [LLMRequest.requestId] so that downstream
 * telemetry can correlate the attempt with its predecessor.
 *
 * ```kotlin
 * // Canonical retry pattern
 * suspend fun streamWithRetry(provider: LLMProvider, request: LLMRequest): Flow<LLMChunk> =
 *     try {
 *         provider.stream(request)
 *     } catch (e: AIError.NetworkError) {
 *         provider.stream(request) // retains request.requestId for correlation
 *     }
 * ```
 */
object StreamContract
