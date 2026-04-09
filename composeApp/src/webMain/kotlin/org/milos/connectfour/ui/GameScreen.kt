package org.milos.connectfour.ui

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text
import org.milos.connectfour.model.GameState
import org.milos.connectfour.model.GameStatus
import org.milos.connectfour.model.Player

@Composable
fun GameScreen(
    state: GameState,
    onColumnClick: (Int) -> Unit,
    onNewGame: () -> Unit
) {
    Div({ classes(AppStyles.appContainer) }) {
        Div({ classes(AppStyles.title) }) { Text("Connect Four") }

        StatusBar(state)

        Board(state, onColumnClick)

        Div({ classes(AppStyles.controls) }) {
            Button({
                classes(AppStyles.button)
                onClick { onNewGame() }
            }) {
                Text("New Game")
            }
        }
    }
}

@Composable
private fun StatusBar(state: GameState) {
    Div({ classes(AppStyles.statusBar) }) {
        when (val status = state.status) {
            is GameStatus.Ongoing -> {
                val textClass = if (state.currentPlayer == Player.ONE)
                    AppStyles.playerOneText else AppStyles.playerTwoText
                val name = playerName(state.currentPlayer)
                Div({ classes(textClass) }) { Text("$name's turn") }
            }
            is GameStatus.Won -> {
                val textClass = if (status.winner == Player.ONE)
                    AppStyles.playerOneText else AppStyles.playerTwoText
                val name = playerName(status.winner)
                Div({ classes(textClass) }) { Text("$name wins!") }
            }
            is GameStatus.Draw -> {
                Div({ classes(AppStyles.drawText) }) { Text("It's a draw!") }
            }
        }
    }
}

private fun playerName(player: Player): String = when (player) {
    Player.ONE -> "Red"
    Player.TWO -> "Yellow"
    Player.NONE -> ""
}
