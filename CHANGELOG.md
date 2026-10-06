# Changelog

All notable changes to this project will be documented in this file.
The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/).

## [Unreleased]

## [3.0.0] - 2026-10-06

### Breaking Changes
- **BREAKING:** `RAGProvider.retrieve(query, topK, options)` is now `retrieve(query, options = RAGOptions())`; the `topK` parameter is removed in favor of `RAGOptions.maxResults`. `options` is no longer nullable. `RAGOptions` is now a regular class (not a `data class`) with explicit `copy`/`equals`/`hashCode`/`toString`; `componentN` destructuring is no longer available.
- **BREAKING:** `NamespacedMemoryManager` methods renamed to align with Flutter/React Native: `add` -> `addToNamespace`, `retrieve` -> `retrieveFromNamespace`, `delete` -> `deleteEntry`, `clear` -> `clearNamespace`. Parameters are unchanged.
- **BREAKING:** `ToolCallRequest` is removed and consolidated into `ToolCall` (identical fields: `id`, `name`, `arguments: JSONObject`). `LLMMessage.toolCalls` is now `List<ToolCall>?`. Replace `ToolCallRequest` with `ToolCall`; no deprecated alias is provided.
- **BREAKING:** `AgentRequest` is now a regular class (not a `data class`) with explicit `copy`, `equals`, `hashCode`, and `toString`; `componentN` destructuring is no longer available. `maxSteps` values below 1 are clamped to 1.
- **BREAKING:** Minimum Foundation dependency raised to `syzygy-foundation-android` 3.0.0 (`foundation: ">=3.0.0"`).

### Added
- `ToolCall` (`id`, `name`, `arguments: JSONObject`) — tool invocation requested by the model; now the single tool-call request type (also used by `LLMMessage.toolCalls`).
- `LLMRequest.tools: List<AgentTool>?` (default `null`) — tools the model may call.
- `LLMResponse.toolCalls: List<ToolCall>?` (default `null`) — tool calls requested by the model.
- `RAGOptions.maxResults` (default 10, values below 1 clamped to 1).

### Changed
- `AgentRequest.maxSteps` is now documented (default 10; exceeding it yields a truncated response).
- `StreamContract` KDoc retry example corrected to use the `Flow` `catch` / `retryWhen` operators (the stream is cold; errors surface during collection).
- Gradle build script modernised: the `val x by tasks.registering(T::class) { }` and `val x by configurations.creating` property delegates replaced with explicit `tasks.register<T>("x") { }` and `configurations.create("ktlintCli")` calls, with the assigned value on its own line after `=` (no deprecated delegate usage).
- Kotlin Gradle plugin and `kotlin-test-junit5` upgraded from 2.0.21 to 2.2.21, which removes the Gradle 9 deprecation warnings about legacy Usage attribute values (`java-api-jars`, `java-runtime-jars`; would fail in Gradle 10).

### Removed
- Redundant `releaseWithSources` Maven publication (`syzygy-ai-android-sources`); the sources jar remains attached to the main `syzygy-ai-android` publication.

## [1.1.0] - 2026-09-24

### Added
- `RAGChunk.id: String?` — optional chunk identifier (default `null`)
- `JSONValue` — shared typed JSON value model, used by `ToolCallRequest.arguments` (`AgentTool` and `AgentStep` still use `Map<String, Any>`)
- `ToolCallRequest` / `ToolCallResult` — structured tool calling contracts
- `AIError` — typed sealed error hierarchy (AuthenticationFailure, RateLimited, NetworkError, InvalidRequest, ProviderFailure, Cancelled)
- `StreamContract` — KDoc object documenting stream semantics (completion, cancellation, partial results, retry)
- `RAGOptions` — retrieval options with `scoreThreshold` and `metadata`
- `ContractParityTest` — compile-check tests for all v1.1.0 contracts

### Changed
- `LLMMessage` — added `toolCalls` and `toolCallResult` fields; `TOOL_CALL` role removed (use `TOOL` role with `toolCalls` field)
- `LLMRequest` — added `requestId` and `correlationId` fields
- `LLMResponse` — added `providerName` and `modelName` fields
- `LLMChunk` — added `providerName` and `modelName` fields
- `RAGChunk` — added optional `id`, `source`, and `documentId` fields
- `NamespacedMemoryManager` — separate interface extending `MemoryManager` with namespaced `add`, `retrieve`, `delete`, and `clear` overloads

## [1.0.0] - 2026-09-22

### Added
- `LLMProvider` — abstract interface for LLM backend integration
- `AgentProtocol` — ReAct loop contract (Reason → Act → Observe)
- `RAGProvider` — retrieval-augmented generation interface
- `MemoryManager` — conversation context management contract

[Unreleased]: https://github.com/Syzygy-Hub/syzygy-ai-android/compare/3.0.0...HEAD
[3.0.0]: https://github.com/Syzygy-Hub/syzygy-ai-android/compare/1.1.0...3.0.0
[1.1.0]: https://github.com/Syzygy-Hub/syzygy-ai-android/compare/1.0.0...1.1.0
[1.0.0]: https://github.com/Syzygy-Hub/syzygy-ai-android/releases/tag/1.0.0
