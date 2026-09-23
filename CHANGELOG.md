# Changelog

All notable changes to this project will be documented in this file.
The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/).

## [Unreleased]

## [1.1.0] - 2026-09-24

### Added
- `RAGChunk.id: String?` — optional chunk identifier (required in v2.0.0)
- `JSONValue` — shared typed JSON value model replacing `Map<String, Any>` in tool contracts
- `ToolCallRequest` / `ToolCallResult` — structured tool calling contracts
- `AIError` — typed sealed error hierarchy (AuthenticationFailure, RateLimited, NetworkError, InvalidRequest, ProviderFailure, Cancelled)
- `StreamContract` — KDoc object documenting stream semantics (completion, cancellation, partial results, retry)
- `RAGOptions` — retrieval options with `scoreThreshold` and `maxResults`
- `ContractParityTest` — compile-check tests for all v1.1.0 contracts

### Changed
- `LLMMessage` — added `toolCalls` and `toolCallResult` fields; `TOOL_CALL` role removed (use `TOOL` role with `toolCalls` field)
- `LLMRequest` — added `requestId` and `correlationId` fields
- `LLMResponse` — added `providerName` and `modelName` fields
- `LLMChunk` — added `providerName` and `modelName` fields
- `RAGChunk` — added required `id` field plus optional `source` and `documentId`
- `NamespacedMemoryManager` — separate interface extending `MemoryManager` with namespaced `add`, `retrieve`, `delete`, and `clear` overloads

## [1.0.0] - 2026-09-22

### Added
- `LLMProvider` — abstract interface for LLM backend integration
- `AgentProtocol` — ReAct loop contract (Reason → Act → Observe)
- `RAGProvider` — retrieval-augmented generation interface
- `MemoryManager` — conversation context management contract
- `StreamHandler` — token streaming abstraction
