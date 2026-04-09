# Connect Four

A web-based implementation of the classic [Connect Four](https://en.wikipedia.org/wiki/Connect_Four) board game, built with **Kotlin/JS** and **Compose HTML**.

Two players take turns dropping colored pieces into a vertical grid. Pieces fall to the lowest available row in the chosen column. The first player to connect enough pieces in a row — horizontally, vertically, or diagonally — wins.

## Features

- **Configurable board** — columns (4–20), rows (4–20), and win condition (Connect 3 through Connect 10)
- **Two-player local play** — alternating turns with standard gravity rules
- **Win and draw detection** — checks horizontal, vertical, and both diagonal directions
- **Drop animation** — CSS keyframe transition for pieces falling into place
- **Persistence** — game state saved to `localStorage`; survives browser refresh
- **Responsive layout** — adapts to desktop and mobile viewports via CSS `clamp()` sizing
- **Keyboard accessible** — columns are focusable and respond to Enter / Space

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Kotlin 2.3 (Multiplatform, JS target) |
| UI | Compose HTML (`org.jetbrains.compose.web`) |
| Serialization | kotlinx.serialization (JSON, for `localStorage` persistence) |
| Build | Gradle with version catalog |

## Prerequisites

- **JDK 17+** (required by the Kotlin/JS Gradle toolchain)

## Getting Started

### Run the app

```shell
# macOS / Linux
./gradlew :composeApp:jsBrowserDevelopmentRun

# Windows
.\gradlew.bat :composeApp:jsBrowserDevelopmentRun
```

The app opens at [http://localhost:8080](http://localhost:8080).

### Run tests

```shell
# macOS / Linux
./gradlew jsTest

# Windows
.\gradlew.bat jsTest
```

### Production build

```shell
# macOS / Linux
./gradlew :composeApp:jsBrowserDistribution

# Windows
.\gradlew.bat :composeApp:jsBrowserDistribution
```

Output is written to `composeApp/build/dist/js/productionExecutable/`.

## Architecture

Game logic is kept **completely pure** — no framework imports, no side effects. All state transitions are functions that take a `GameState` and return a new `GameState`.

```
                   ┌─────────────┐
                   │ ConfigScreen│  (board size, win condition)
                   └──────┬──────┘
                          │ GameConfig
                          ▼
┌──────────┐  column  ┌──────────┐  GameState  ┌──────────────┐
│  Board   │ ───────► │   App    │ ◄──────────► │ GameStorage  │
│  + Cell  │  click   │ (state)  │  save/load   │ (localStorage│)
└──────────┘          └────┬─────┘              └──────────────┘
                           │ dropPiece()
                           ▼
                     ┌───────────┐
                     │ GameLogic │  (pure functions)
                     └───────────┘
```

## Testing

16 unit tests cover the core game logic:

- Win detection in all four directions (horizontal, vertical, both diagonals)
- Draw detection on a fully filled board
- Gravity mechanics and player alternation
- Edge cases: full column, out-of-bounds column, moves after game over
- Custom win conditions (e.g. Connect 5 on a 10x10 board)
- Initial state correctness

## Project Structure

```
composeApp/src/
├── webMain/kotlin/org/milos/connectfour/
│   ├── main.kt              Entry point (renderComposable)
│   ├── model/
│   │   ├── Player.kt        Player enum (ONE, TWO, NONE)
│   │   └── GameState.kt     GameConfig, GameStatus, GameState
│   ├── logic/
│   │   └── GameLogic.kt     Pure functions: newGame, dropPiece, checkWin, checkDraw
│   ├── storage/
│   │   └── GameStorage.kt   localStorage persistence via kotlinx.serialization
│   └── ui/
│       ├── App.kt           Root composable and state management
│       ├── AppStyles.kt     Global styles (layout, forms, buttons)
│       ├── BoardStyles.kt   Board grid, cell, and drop-animation styles
│       ├── ConfigScreen.kt  Board configuration form with validation
│       ├── GameScreen.kt    Game view with status bar and controls
│       ├── Board.kt         Board grid composable
│       └── Cell.kt          Individual cell composable
└── webTest/kotlin/org/milos/connectfour/
    └── logic/
        └── GameLogicTest.kt 16 tests for core game logic
```
