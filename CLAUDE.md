# Minuet — Android Project Guide

## Project Overview

**Minuet** is a native Android app for managing music repertoire.
- Package: `com.dosei.music.repertoire`
- Language: Kotlin
- UI: Jetpack Compose + Material3
- minSdk: 35 | targetSdk: 36
- Build system: Gradle with Kotlin DSL + Version Catalog (`gradle/libs.versions.toml`)

---

## Architecture

Follow **Clean Architecture** with **MVVM** on the presentation layer.

```
app/
└── src/main/java/com/dosei/music/repertoire/
    ├── data/           # Implementations: repositories, DAOs, remote sources, mappers
    ├── domain/         # Business logic: entities, use cases, repository interfaces
    ├── ui/             # Presentation: screens, ViewModels, Composables, navigation
    │   ├── theme/      # Theme, Color, Type, Shape
    │   └── <feature>/  # One package per feature (e.g., song/, setlist/)
    └── di/             # Dependency injection modules (Hilt)
```

### Layer rules
- `domain` has **zero Android dependencies** — pure Kotlin only.
- `data` depends on `domain`; never the reverse.
- `ui` depends on `domain` only via ViewModels; never accesses `data` directly.
- Use cases contain a single public `invoke()` method.

---

## Tech Stack Decisions

### Must use
| Concern | Library |
|---|---|
| DI | Hilt |
| Navigation | Navigation Compose |
| Async | Kotlin Coroutines + Flow |
| Local DB | Room |
| ViewModel | `androidx.lifecycle:lifecycle-viewmodel-compose` |
| State | `StateFlow` / `UiState` sealed class |

### Add to `libs.versions.toml` before adding any new dependency.

---

## Compose Conventions

- One file per screen: `<Feature>Screen.kt` (stateful) + `<Feature>Content.kt` (stateless/preview-friendly).
- ViewModels expose a single `uiState: StateFlow<UiState>` and a `onEvent(event: UiEvent)` function.
- Hoisting: keep state at the ViewModel level; pass lambdas down to Composables.
- Every Composable that renders real UI must have a `@Preview`.
- Use `Modifier` as the last non-lambda parameter, defaulting to `Modifier`.
- Prefer `LazyColumn`/`LazyRow` over `Column`/`Row` for lists.
- Never call `ViewModel` methods directly from nested Composables — pass lambdas.

### UiState pattern
```kotlin
sealed interface SongListUiState {
    data object Loading : SongListUiState
    data class Success(val songs: List<Song>) : SongListUiState
    data class Error(val message: String) : SongListUiState
}
```

---

## Kotlin Conventions

- Prefer `data class` for domain entities and UI models.
- Use `sealed class` / `sealed interface` for state and events.
- Use `object` only for singletons with no state.
- Avoid nullable types where possible; use `Result<T>` or sealed classes for error handling.
- Extension functions live in a file named `<Type>Extensions.kt` inside the relevant package.
- No business logic in Activities or Composables — delegate to ViewModel/use cases.

---

## Build & Gradle

- All dependency versions are declared in `gradle/libs.versions.toml`. Never hardcode versions in `build.gradle.kts`.
- Use `alias(libs.xxx)` to reference dependencies.
- Multi-module: when adding a second feature that is clearly independent, create a new Gradle module instead of growing the `:app` module.
- Keep `isMinifyEnabled = true` for release builds and maintain `proguard-rules.pro`.

---

## Testing

- **Unit tests** (`src/test/`): test use cases and ViewModels. Use JUnit4 + MockK + Turbine for Flow testing.
- **Integration tests** (`src/androidTest/`): test Room DAOs and navigation flows. Use AndroidX Test.
- **UI tests**: use Compose Testing APIs (`composeTestRule`).
- Aim for use cases and ViewModels to have > 80% coverage.
- Test file mirrors source: `SongListViewModel` → `SongListViewModelTest`.

### Run tests
```bash
./gradlew test                        # unit tests
./gradlew connectedAndroidTest        # instrumented tests
./gradlew lint                        # static analysis
```

---

## Code Style

- Follow [Kotlin coding conventions](https://kotlinlang.org/docs/coding-conventions.html).
- Max line length: 120 characters.
- Imports: no wildcard imports.
- No commented-out code committed to the repo.
- Resource names follow the pattern: `<type>_<feature>_<description>` (e.g., `ic_song_play`, `str_song_title`).

---

## Do's and Don'ts

### Do
- Use `rememberSaveable` for UI state that must survive configuration changes in Composables.
- Use `collectAsStateWithLifecycle()` (from `lifecycle-runtime-compose`) instead of `collectAsState()`.
- Use `Dispatchers.IO` for I/O operations inside coroutines.
- Apply `edge-to-edge` display (`enableEdgeToEdge()` is already set in `MainActivity`).

### Don't
- Don't use `LiveData` — use `StateFlow` and `SharedFlow`.
- Don't use `SharedPreferences` directly — wrap in a `DataStore` repository.
- Don't access `Context` inside ViewModels (use `AndroidViewModel` only as a last resort).
- Don't hardcode strings visible to the user — use `strings.xml`.
- Don't create God-classes. If a ViewModel exceeds ~300 lines, split the feature.
- Don't use `GlobalScope` — use structured concurrency with `viewModelScope` or `lifecycleScope`.