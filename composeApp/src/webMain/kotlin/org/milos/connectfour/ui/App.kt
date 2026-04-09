package org.milos.connectfour.ui

import androidx.compose.runtime.*
import org.milos.connectfour.logic.dropPiece
import org.milos.connectfour.logic.newGame
import org.milos.connectfour.model.GameState
import org.milos.connectfour.storage.GameStorage

@Composable
fun App() {
    var gameState by remember { mutableStateOf(GameStorage.load()?.copy(lastMove = null)) }

    val currentState = gameState
    if (currentState == null) {
        ConfigScreen { config ->
            val state = newGame(config)
            GameStorage.save(state)
            gameState = state
        }
    } else {
        GameScreen(
            state = currentState,
            onColumnClick = { col ->
                val updated = dropPiece(currentState, col)
                if (updated !== currentState) {
                    GameStorage.save(updated)
                    gameState = updated
                }
            },
            onNewGame = {
                GameStorage.clear()
                gameState = null
            }
        )
    }
}
