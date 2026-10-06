[![JVM](https://img.shields.io/badge/JVM-17-7F77DD?style=flat)](https://openjdk.org/) [![Kotlin](https://img.shields.io/badge/Kotlin-2.0+-1D9E75?logo=kotlin&logoColor=white&style=flat)](https://kotlinlang.org) [![CI](https://img.shields.io/github/actions/workflow/status/Syzygy-Hub/syzygy-ai-android/ci.yml?label=ci&style=flat)](https://github.com/Syzygy-Hub/syzygy-ai-android/actions/workflows/ci.yml) [![Version](https://img.shields.io/badge/version-3.0.0-D85A30?style=flat)](https://github.com/Syzygy-Hub/syzygy-ai-android/releases) [![License](https://img.shields.io/badge/License-MIT-green?style=flat)](LICENSE)

<picture>
  <source media="(prefers-color-scheme: dark)" srcset="https://raw.githubusercontent.com/Syzygy-Hub/.github/main/brand/assets/banners/syzygy-banner-dark-1200.png">
  <img src="https://raw.githubusercontent.com/Syzygy-Hub/.github/main/brand/assets/banners/syzygy-banner-light-1200.png" alt="Syzygy" width="600">
</picture>

# syzygy-ai-android

The AI layer of the Syzygy ecosystem — providing LLMProvider, AgentProtocol, EmbeddingProvider, RAGProvider, and MemoryManager contracts for Android.

> **v3.0.0 — Tool Calling, `RAGOptions.maxResults` & Aligned Memory Naming**
> Adds `LLMRequest.tools` / `LLMResponse.toolCalls`, a single `ToolCall` type, `RAGOptions.maxResults` for retrieval size, and renamed `NamespacedMemoryManager` methods. Builds on `JSONValue`, `AIError`, `ToolCallResult`, `StreamContract` (KDoc), and enriched `RAGChunk`. See [CHANGELOG.md](CHANGELOG.md) for breaking changes.
>
> `RAGChunk.id: String?` is an optional field (null by default).

> **Pure Contracts Only**
> This library contains interface and data class definitions only. No concrete implementations are included. Implementations targeting specific LLM backends, vector stores, or memory systems should depend on this package and provide their own conforming types.

## About

syzygy-ai-android defines the AI contracts that downstream modules implement. It depends only on `syzygy-foundation-android` and provides the abstraction layer for LLM backends, agent loops, retrieval-augmented generation, memory management, and streaming (via `LLMProvider.stream` returning a `Flow`). No concrete implementations ship here — conforming implementations live in dedicated service modules.

## Role in the Syzygy Ecosystem

`syzygy-ai-android` is a peer layer that depends on Foundation and nothing else. It exposes AI contracts that application modules and AI service implementations depend on.

Full ecosystem architecture: [ecosystem-fragment.md](https://github.com/Syzygy-Hub/.github/blob/main/docs/ecosystem-fragment.md)

### Contracts

| Contract | Description |
|---|---|
| `LLMProvider` | Abstract interface for LLM backend integration |
| `AgentProtocol` | ReAct loop contract (Reason → Act → Observe) |
| `RAGProvider` | Retrieval-augmented generation interface |
| `MemoryManager` | Conversation context management contract |
| `EmbeddingProvider` | Abstract interface for generating text embeddings |

### NamespacedMemoryManager

`NamespacedMemoryManager` extends `MemoryManager` with namespace-scoped operations using distinct method names, aligned across Android, iOS, Flutter, and React Native: `addToNamespace(entry, namespace)`, `retrieveFromNamespace(query, namespace, limit)`, `deleteEntry(id, namespace)`, and `clearNamespace(namespace)`.

### RAG

`RAGProvider.retrieve(query, options)` returns relevant `RAGChunk`s. Result size is controlled by `RAGOptions.maxResults` (default 10, values below 1 are clamped to 1):

```kotlin
val chunks = provider.retrieve("what is syzygy?", RAGOptions(scoreThreshold = 0.5, maxResults = 5))
```

### Tool calling

`LLMRequest.tools` (`List<AgentTool>?`) advertises tools to the model; `LLMResponse.toolCalls` (`List<ToolCall>?`) carries the calls the model requested. Both default to `null`.

## Release Process

Releases follow the Syzygy tag-push release flow:

1. Create a `release/X.X.X` branch
2. Bump the version in `syzygy.yml`, `build.gradle.kts` (`syzygyVersion` variable), `SyzygyAI.VERSION` (`src/main/kotlin/com/syzygy/ai/SyzygyAI.kt`), the README badge and install snippet, and `CHANGELOG.md`
3. Open a PR to `main` and wait for CI to pass
4. Merge the PR
5. Push the tag: `git tag X.X.X` and `git push origin X.X.X`
6. The tag push triggers the org-level release workflow which validates `syzygy.yml` matches the tag, extracts the CHANGELOG entry, and creates the GitHub Release. JitPack auto-builds from the tag.

For the full release standard see the [Syzygy-Hub/.github release standard](https://github.com/Syzygy-Hub/.github/blob/main/engineering/standards/release-standard.md).

## Platforms

| Platform | Min Version | Package Manager | Status |
|---|---|---|---|
| Android / JVM | JVM 17 | JitPack | ✅ Supported |

## Requirements

- Kotlin 2.0+ (built with Kotlin 2.2.21)
- JVM 17 (published as a JVM 17 JAR)

> **Note:** This library uses the Kotlin JVM plugin and publishes as a JAR (not an Android AAR). This is intentional for a pure-contracts library with no Android framework dependencies. Consumers on Android can add it as a JVM dependency directly.

## Installation

```kotlin
// In settings.gradle.kts
dependencyResolutionManagement {
    repositories {
        maven { url = uri("https://jitpack.io") }
    }
}

// In build.gradle.kts
implementation("com.github.Syzygy-Hub:syzygy-ai-android:3.0.0")
```

## Foundation Dependency

`syzygy-ai-android` depends on `syzygy-foundation-android` via `api` (transitive), so you do not need to declare Foundation separately when you already depend on AI.

**Depends on:** `syzygy-foundation-android` >= 3.0.0

**Used by:** application modules and AI service implementations.

## Development Setup

> **JDK requirement:** CI targets JDK 17. Local builds should use JDK 17 to match CI.

After cloning, install the pre-push hook to run a Gradle build check before every push:

```bash
bash scripts/install-hooks.sh
```

The hook runs `./gradlew build` and blocks the push if the build fails. To bypass in an emergency: `git push --no-verify`.

## Contributing

Contributions are welcome. Please follow the [Syzygy engineering standards](https://github.com/Syzygy-Hub/.github/tree/main/engineering/standards) when submitting pull requests.

## License

MIT — see [LICENSE](LICENSE)
