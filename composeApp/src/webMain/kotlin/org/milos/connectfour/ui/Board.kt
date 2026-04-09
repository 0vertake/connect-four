package org.milos.connectfour.ui

import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div
import androidx.compose.runtime.Composable
import org.milos.connectfour.model.GameState
import org.milos.connectfour.model.GameStatus
import org.milos.connectfour.model.Player

@Composable
fun Board(state: GameState, onColumnClick: (Int) -> Unit) {
    val cols = state.config.cols
    val rows = state.config.rows
    val isOngoing = state.status is GameStatus.Ongoing
    val cellSize = "clamp(1.5rem, min(calc(90vw / $cols), calc(60vh / $rows)), 5rem)"

    Div({
        classes(BoardStyles.board)
        attr("role", "grid")
        attr("aria-label", "Connect Four board, $cols columns by $rows rows")
        style {
            property("grid-template-columns", "repeat($cols, auto)")
        }
    }) {
        for (col in 0 until cols) {
            Div({
                classes(BoardStyles.column)
                if (!isOngoing) classes(BoardStyles.columnDisabled)
                attr("role", "button")
                attr("aria-label", "Column ${col + 1}")
                if (isOngoing) {
                    tabIndex(0)
                    onClick { onColumnClick(col) }
                    onKeyDown { event ->
                        if (event.key == "Enter" || event.key == " ") {
                            event.preventDefault()
                            onColumnClick(col)
                        }
                    }
                } else {
                    attr("aria-disabled", "true")
                }
            }) {
                for (row in (rows - 1) downTo 0) {
                    val player = state.board[col][row]
                    val isDropping = state.lastMove == Pair(col, row)
                    val fallDistance = rows - row - 1
                    Cell(player, isDropping, cellSize, fallDistance)
                }
            }
        }
    }
}
