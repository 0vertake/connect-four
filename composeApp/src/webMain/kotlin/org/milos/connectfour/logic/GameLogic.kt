package org.milos.connectfour.logic

import org.milos.connectfour.model.GameConfig
import org.milos.connectfour.model.GameState
import org.milos.connectfour.model.GameStatus
import org.milos.connectfour.model.Player

fun newGame(config: GameConfig): GameState {
    val board = List(config.cols) { List(config.rows) { Player.NONE } }
    return GameState(
        config = config,
        board = board,
        currentPlayer = Player.ONE,
        status = GameStatus.Ongoing,
        lastMove = null
    )
}

fun dropPiece(state: GameState, col: Int): GameState {
    if (state.status != GameStatus.Ongoing) return state
    if (col !in state.board.indices) return state

    val column = state.board[col]
    val row = column.indexOfFirst { it == Player.NONE }
    if (row == -1) return state

    val newColumn = column.toMutableList().apply { this[row] = state.currentPlayer }
    val newBoard = state.board.toMutableList().apply { this[col] = newColumn.toList() }

    val won = checkWin(newBoard, col, row, state.currentPlayer, state.config.winCondition)
    val draw = !won && checkDraw(newBoard)

    val newStatus = when {
        won -> GameStatus.Won(state.currentPlayer)
        draw -> GameStatus.Draw
        else -> GameStatus.Ongoing
    }

    val nextPlayer = if (newStatus is GameStatus.Ongoing) {
        if (state.currentPlayer == Player.ONE) Player.TWO else Player.ONE
    } else {
        state.currentPlayer
    }

    return state.copy(
        board = newBoard,
        currentPlayer = nextPlayer,
        status = newStatus,
        lastMove = Pair(col, row)
    )
}

internal fun checkWin(
    board: List<List<Player>>,
    col: Int,
    row: Int,
    player: Player,
    winCondition: Int
): Boolean {
    val directions = listOf(
        Pair(1, 0),  // horizontal
        Pair(0, 1),  // vertical
        Pair(1, 1),  // diagonal down-right
        Pair(1, -1)  // diagonal up-right
    )

    for ((dc, dr) in directions) {
        var count = 1
        count += countInDirection(board, col, row, dc, dr, player)
        count += countInDirection(board, col, row, -dc, -dr, player)
        if (count >= winCondition) return true
    }

    return false
}

private fun countInDirection(
    board: List<List<Player>>,
    col: Int,
    row: Int,
    dc: Int,
    dr: Int,
    player: Player
): Int {
    var c = col + dc
    var r = row + dr
    var count = 0
    while (c in board.indices && r in board[c].indices && board[c][r] == player) {
        count++
        c += dc
        r += dr
    }
    return count
}

internal fun checkDraw(board: List<List<Player>>): Boolean =
    board.all { column -> column.all { it != Player.NONE } }
