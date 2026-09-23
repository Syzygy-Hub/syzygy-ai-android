package com.syzygy.ai.contracts

sealed class AIError(
    message: String,
    cause: Throwable? = null,
) : Exception(message, cause) {
    class AuthenticationFailure(message: String) : AIError(message)

    class RateLimited(val retryAfterMs: Long?) : AIError("Rate limited") // retry-after in milliseconds

    class NetworkError(cause: Throwable) : AIError("Network error", cause)

    class InvalidRequest(message: String) : AIError(message)

    class ProviderFailure(message: String) : AIError(message)

    object Cancelled : AIError("Cancelled")
}
