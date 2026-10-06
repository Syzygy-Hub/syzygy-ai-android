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
 * [LLMProvider.stream] returns a cold [Flow]: calling it does not start any work, and
 * errors surface during collection, not at the call site. A try/catch around the
 * `stream()` call therefore never observes them; use the [kotlinx.coroutines.flow.catch]
 * and [kotlinx.coroutines.flow.retryWhen] Flow operators instead.
 *
 * On [AIError.NetworkError], callers may retry by re-collecting the stream with the same
 * [LLMRequest], preserving the original [LLMRequest.requestId] so that downstream
 * telemetry can correlate the attempt with its predecessor. Note that a retry restarts the
 * stream from the beginning, so chunks already emitted will be emitted again.
 *
 * ```kotlin
 * // Canonical retry pattern: errors arrive while the Flow is collected.
 * fun streamWithRetry(provider: LLMProvider, request: LLMRequest): Flow<LLMChunk> =
 *     provider.stream(request) // cold: nothing runs until collected
 *         .retryWhen { cause, attempt ->
 *             cause is AIError.NetworkError && attempt < 3 // same request => same requestId
 *         }
 *         .catch { e ->
 *             // Reached for non-retryable errors or once retries are exhausted.
 *             throw e
 *         }
 * ```
 */
object StreamContract
