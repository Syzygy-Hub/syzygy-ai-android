[![Android](https://img.shields.io/badge/Android-API%2024+-7F77DD?style=flat)](https://developer.android.com/) [![Kotlin](https://img.shields.io/badge/Kotlin-1.9+-1D9E75?logo=kotlin&logoColor=white&style=flat)](https://kotlinlang.org) [![CI](https://img.shields.io/github/actions/workflow/status/Syzygy-Hub/syzygy-ai-android/ci.yml?label=ci&style=flat)](https://github.com/Syzygy-Hub/syzygy-ai-android/actions/workflows/ci.yml) [![Version](https://img.shields.io/badge/version-1.0.0-D85A30?style=flat)](https://github.com/Syzygy-Hub/syzygy-ai-android/releases) [![License](https://img.shields.io/badge/License-MIT-green?style=flat)](LICENSE)

<picture>
  <source media="(prefers-color-scheme: dark)" srcset="https://raw.githubusercontent.com/Syzygy-Hub/.github/main/brand/assets/banners/syzygy-banner-dark-1200.png">
  <img src="https://raw.githubusercontent.com/Syzygy-Hub/.github/main/brand/assets/banners/syzygy-banner-light-1200.png" alt="Syzygy" width="600">
</picture>

# syzygy-ai-android

The AI layer of the Syzygy ecosystem — providing LLMProvider, AgentProtocol, RAGProvider, MemoryManager, and StreamHandler contracts for Android.

## About

syzygy-ai-android defines the AI contracts that downstream modules implement. It depends only on `syzygy-foundation-android` and provides the abstraction layer for LLM backends, agent loops, retrieval-augmented generation, memory management, and token streaming. No concrete implementations ship here — conforming implementations live in dedicated service modules.

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
| `StreamHandler` | Token streaming abstraction |

## Release Process

Releases follow the Syzygy tag-push release flow:

1. Create a `release/X.X.X` branch
2. Bump the version in `syzygy.yml`, `build.gradle.kts` (`syzygyVersion` variable), the README badge, and `CHANGELOG.md`
3. Open a PR to `main` and wait for CI to pass
4. Merge the PR
5. Push the tag: `git tag X.X.X` and `git push origin X.X.X`
6. The tag push triggers the org-level release workflow which validates `syzygy.yml` matches the tag, extracts the CHANGELOG entry, and creates the GitHub Release. JitPack auto-builds from the tag.

For the full release standard see the [Syzygy-Hub/.github release standard](https://github.com/Syzygy-Hub/.github/blob/main/engineering/standards/release-standard.md).

## Platforms

| Platform | Min Version | Package Manager | Status |
|---|---|---|---|
| Android | API 24+ | JitPack | ✅ Supported |

## Requirements

- Android API 24+
- Kotlin 1.9+
- Android Studio Ladybug or later

## Installation

```kotlin
// In settings.gradle.kts
dependencyResolutionManagement {
    repositories {
        maven { url = uri("https://jitpack.io") }
    }
}

// In build.gradle.kts
implementation("com.github.Syzygy-Hub:syzygy-ai-android:1.0.0")
```

## Foundation Dependency

`syzygy-ai-android` depends on `syzygy-foundation-android` via `api` (transitive), so you do not need to declare Foundation separately when you already depend on AI.

**Depends on:** `syzygy-foundation-android` >= 1.2.0

**Used by:** application modules and AI service implementations.

## Contributing

Contributions are welcome. Please follow the [Syzygy engineering standards](https://github.com/Syzygy-Hub/.github/tree/main/engineering/standards) when submitting pull requests.

## License

MIT — see [LICENSE](LICENSE)
