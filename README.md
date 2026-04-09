# Connect Four

A web-based implementation of the classic [Connect Four](https://en.wikipedia.org/wiki/Connect_Four) board game, built with **Kotlin/JS** and **Compose HTML**.

## Features

- **Configurable board** — choose columns (4–20), rows (4–20), and win condition (Connect 3 through Connect 10)
- **Two-player local play** — alternating turns with standard gravity rules
- **Win and draw detection** — checks horizontal, vertical, and both diagonal directions
- **Drop animation** — CSS keyframe transition for pieces falling into place
- **Persistence** — game state is saved to `localStorage` and survives browser refresh
- **Responsive layout** — adapts to desktop and mobile viewports via CSS `clamp()` sizing

## Tech Stack

- Kotlin Multiplatform (JS target)
- Compose HTML (`org.jetbrains.compose.web`)
- kotlinx.serialization (JSON persistence)

## Getting Started

### Run the app

```shell
# macOS / Linux
./gradlew :composeApp:jsBrowserDevelopmentRun

# Windows
.\gradlew.bat :composeApp:jsBrowserDevelopmentRun
```

The app opens at `http://localhost:8080`.

### Run tests

```shell
# macOS / Linux
./gradlew jsTest

# Windows
.\gradlew.bat jsTest
```

## Project Structure

```
composeApp/src/webMain/kotlin/org/milos/connectfour/
├── main.kt                 # Entry point
├── model/
│   ├── Player.kt           # Player enum (ONE, TWO, NONE)
│   └── GameState.kt        # GameConfig, GameStatus, GameState
├── logic/
│   └── GameLogic.kt        # Pure game logic (newGame, dropPiece, win/draw checks)
├── storage/
│   └── GameStorage.kt      # localStorage persistence
└── ui/
    ├── App.kt              # Root composable, state management
    ├── AppStyles.kt        # Global styles (layout, forms, buttons)
    ├── BoardStyles.kt      # Board, cell, and animation styles
    ├── ConfigScreen.kt     # Board configuration form
    ├── GameScreen.kt       # Game view with status bar
    ├── Board.kt            # Board grid composable
    └── Cell.kt             # Individual cell composable
```
