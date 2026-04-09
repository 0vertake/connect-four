# PROGRESS.md — Connect Four Implementation Checklist

Update this file as you complete steps. Mark done items with [x].

---

## Phase 1 — Data Model & Pure Logic
- [x] Create `model/Player.kt` — `Player` enum (ONE, TWO, NONE)
- [x] Create `model/GameState.kt` — `GameConfig`, `GameStatus`, `GameState` data classes, annotated with `@Serializable`
- [x] Create `logic/GameLogic.kt` — implement `newGame(config)`
- [x] Implement `dropPiece(state, col)` — find lowest empty row in col, return new state; no-op if column full or game over
- [x] Implement `checkWin(board, col, row, player, winCondition)` — check horizontal, vertical, diagonal-down, diagonal-up from placed piece only
- [x] Implement `checkDraw(board)` — true if all columns are full
- [x] Wire `dropPiece` to call `checkWin` and `checkDraw` and set `GameStatus` accordingly

## Phase 2 — Unit Tests
- [x] Create `webTest/kotlin/logic/GameLogicTest.kt`
- [x] Test: horizontal win detection
- [x] Test: vertical win detection
- [x] Test: diagonal win detection (both directions)
- [x] Test: draw detection (full board, no winner)
- [x] Test: `dropPiece` respects gravity (fills from bottom)
- [x] Test: `dropPiece` is a no-op on a full column
- [x] Test: `dropPiece` is a no-op when game is already won/drawn

## Phase 3 — Styles
- [x] Create `ui/AppStyles.kt` with a `StyleSheet` object
- [x] Define CSS variables: player colours (red/yellow), board colour (blue), background, cell size
- [x] Add `.board` style — CSS grid, gap, padding, border-radius
- [x] Add `.cell` style — circular, background = empty colour, transition for colour change
- [x] Add `.cell-player-one` and `.cell-player-two` colour overrides
- [x] Add `@keyframes fall` animation and `.dropping` class (translateY from -400% to 0, 300ms ease-in)
- [x] Add responsive cell sizing using `clamp()` based on viewport
- [x] Add `.column` hover style — subtle background highlight to indicate clickable

## Phase 4 — UI Components
- [x] Create `ui/Cell.kt` — composable, applies correct CSS classes including `.dropping` if it matches `lastMove`
- [x] Create `ui/Board.kt` — renders CSS grid of columns; each column is a `Div` with `onClick` calling `onColumnClick(col)`; disable clicks if game not ongoing
- [x] Create `ui/ConfigScreen.kt` — number inputs for cols, rows, win condition with validation; "Start Game" button; shows validation errors inline
- [x] Create `ui/GameScreen.kt` — status bar (current turn / winner / draw), `Board`, and "New Game" button
- [x] Create `ui/App.kt` — top-level composable; holds `GameState` in `remember { mutableStateOf(...) }`; routes between `ConfigScreen` and `GameScreen`; calls `GameStorage` on every state change

## Phase 5 — Persistence
- [x] Create `storage/GameStorage.kt`
- [x] Implement `GameStorage.save(state: GameState)` — serialize to JSON via `kotlinx.serialization`, write to `localStorage["connect_four_state"]`
- [x] Implement `GameStorage.load(): GameState?` — read and deserialize; return null on missing key or any exception
- [x] In `App.kt`, load state on first composition; fall back to config screen if null

## Phase 6 — Entry Point & Wiring
- [x] Verify `main.kt` calls `renderComposable(rootElementId = "root") { App() }` (update if scaffold differs)
- [x] Register `AppStyles` stylesheet in `main.kt`
- [x] Verify the app compiles and runs in browser via `./gradlew jsBrowserDevelopmentRun` or equivalent

## Phase 7 — Polish & Verification
- [x] Confirm falling animation is visible on piece drop
- [x] Confirm game state survives browser refresh (persistence working)
- [x] Confirm layout is usable on a narrow viewport (< 400px width)
- [x] Confirm win is detected correctly for all 4 directions with a custom win condition (e.g., Connect 5)
- [x] Confirm draw is detected on a full board with no winner
- [x] Confirm column click is disabled after game ends
- [x] Run tests: `./gradlew jsTest` — all pass (14/14)

---

## Notes / Decisions Log
- Removed `wasmJs` target — Compose HTML only supports Kotlin/JS. The `wasmJsMain` source set is unused.
- Switched from `ComposeViewport` (canvas-based Compose UI) to `renderComposable` (DOM-based Compose HTML) per `.cursorrules`.
- Replaced Compose UI dependencies (Material3, Foundation, etc.) with `compose.html.core` in `build.gradle.kts`.
- Added `kotlinx.serialization` plugin and `kotlinx-serialization-json` dependency for persistence.
- Used `data object` for `GameStatus.Ongoing` and `GameStatus.Draw` (instead of `object`) for proper `@Serializable` support.
- `Label` in Compose HTML requires `forId` as first positional parameter; used named `attrs =` parameter.
- Cell sizing is done inline via `clamp()` rather than CSS custom properties, since board dimensions are known at composition time.
- Test command is `./gradlew jsTest` (not `webTest`), since only the JS target exists.
