package org.milos.connectfour.logic

import org.milos.connectfour.model.GameConfig
import org.milos.connectfour.model.GameState
import org.milos.connectfour.model.GameStatus
import org.milos.connectfour.model.Player
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class GameLogicTest {

    private val defaultConfig = GameConfig(cols = 7, rows = 6, winCondition = 4)

    private fun dropSequence(cols: List<Int>, config: GameConfig = defaultConfig): GameState {
        var state = newGame(config)
        for (col in cols) {
            state = dropPiece(state, col)
        }
        return state
    }

    @Test
    fun horizontalWinDetection() {
        val state = dropSequence(listOf(0, 0, 1, 1, 2, 2, 3))
        val status = assertIs<GameStatus.Won>(state.status)
        assertEquals(Player.ONE, status.winner)
    }

    @Test
    fun verticalWinDetection() {
        val state = dropSequence(listOf(0, 1, 0, 1, 0, 1, 0))
        val status = assertIs<GameStatus.Won>(state.status)
        assertEquals(Player.ONE, status.winner)
    }

    @Test
    fun diagonalUpRightWinDetection() {
        // P1 at positions: (0,0), (1,1), (2,2), (3,3)
        val state = dropSequence(listOf(
            0,    // P1@(0,0)
            1,    // P2@(1,0)
            1,    // P1@(1,1)
            2,    // P2@(2,0)
            3,    // P1@(3,0)
            2,    // P2@(2,1)
            2,    // P1@(2,2)
            3,    // P2@(3,1)
            3,    // P1@(3,2)
            4,    // P2@(4,0)
            3,    // P1@(3,3) — diagonal win!
        ))
        val status = assertIs<GameStatus.Won>(state.status)
        assertEquals(Player.ONE, status.winner)
    }

    @Test
    fun diagonalDownLeftWinDetection() {
        // P1 at (3,0), (2,1), (1,2), (0,3)
        val state = dropSequence(listOf(
            3,    // P1@(3,0)
            2,    // P2@(2,0)
            2,    // P1@(2,1)
            1,    // P2@(1,0)
            0,    // P1@(0,0)
            1,    // P2@(1,1)
            1,    // P1@(1,2)
            0,    // P2@(0,1)
            0,    // P1@(0,2)
            4,    // P2@(4,0)
            0,    // P1@(0,3) — diagonal win!
        ))
        val status = assertIs<GameStatus.Won>(state.status)
        assertEquals(Player.ONE, status.winner)
    }

    @Test
    fun drawDetection() {
        // 4x4 board filled with no 4-in-a-row anywhere.
        // Final board (row 0 = bottom):
        //   Col 0: P1 P2 P1 P2
        //   Col 1: P1 P2 P1 P2
        //   Col 2: P2 P1 P2 P1
        //   Col 3: P2 P1 P2 P1
        val config = GameConfig(cols = 4, rows = 4, winCondition = 4)
        val state = dropSequence(
            listOf(0, 2, 1, 3, 2, 0, 3, 1, 0, 2, 1, 3, 2, 0, 3, 1),
            config
        )
        assertIs<GameStatus.Draw>(state.status)
    }

    @Test
    fun dropPieceRespectsGravity() {
        val state = newGame(defaultConfig)
        val after = dropPiece(state, 3)
        assertEquals(Player.ONE, after.board[3][0])
        assertEquals(Player.NONE, after.board[3][1])

        val after2 = dropPiece(after, 3)
        assertEquals(Player.ONE, after2.board[3][0])
        assertEquals(Player.TWO, after2.board[3][1])
        assertEquals(Player.NONE, after2.board[3][2])
    }

    @Test
    fun dropPieceIsNoOpOnFullColumn() {
        val config = GameConfig(cols = 7, rows = 3, winCondition = 4)
        var state = newGame(config)
        state = dropPiece(state, 0) // P1 row 0
        state = dropPiece(state, 0) // P2 row 1
        state = dropPiece(state, 0) // P1 row 2 — column full
        val beforeDrop = state
        state = dropPiece(state, 0) // should be no-op
        assertEquals(beforeDrop, state)
    }

    @Test
    fun dropPieceIsNoOpWhenGameIsOver() {
        val state = dropSequence(listOf(0, 0, 1, 1, 2, 2, 3)) // P1 wins
        assertIs<GameStatus.Won>(state.status)
        val afterExtraDrop = dropPiece(state, 4)
        assertEquals(state, afterExtraDrop)
    }

    @Test
    fun customWinConditionConnect5() {
        val config = GameConfig(cols = 10, rows = 10, winCondition = 5)
        val state = dropSequence(listOf(0, 0, 1, 1, 2, 2, 3, 3, 4), config)
        val status = assertIs<GameStatus.Won>(state.status)
        assertEquals(Player.ONE, status.winner)
    }

    @Test
    fun fourInARowDoesNotWinConnect5() {
        val config = GameConfig(cols = 10, rows = 10, winCondition = 5)
        val state = dropSequence(listOf(0, 0, 1, 1, 2, 2, 3), config)
        assertIs<GameStatus.Ongoing>(state.status)
    }

    @Test
    fun playerTwoCanWin() {
        // P1 plays col 0, P2 plays col 1, ..., P2 gets 4 in a row on bottom
        val state = dropSequence(listOf(0, 1, 0, 2, 0, 3, 6, 4))
        val status = assertIs<GameStatus.Won>(state.status)
        assertEquals(Player.TWO, status.winner)
    }

    @Test
    fun playerAlternatesAfterEachDrop() {
        var state = newGame(defaultConfig)
        assertEquals(Player.ONE, state.currentPlayer)

        state = dropPiece(state, 0)
        assertEquals(Player.TWO, state.currentPlayer)

        state = dropPiece(state, 1)
        assertEquals(Player.ONE, state.currentPlayer)

        state = dropPiece(state, 2)
        assertEquals(Player.TWO, state.currentPlayer)
    }

    @Test
    fun outOfBoundsColumnIsNoOp() {
        val state = newGame(defaultConfig)
        val afterNegative = dropPiece(state, -1)
        assertEquals(state, afterNegative)

        val afterTooLarge = dropPiece(state, defaultConfig.cols)
        assertEquals(state, afterTooLarge)
    }

    @Test
    fun lastMoveIsTrackedCorrectly() {
        var state = newGame(defaultConfig)
        assertEquals(null, state.lastMove)

        state = dropPiece(state, 3)
        assertEquals(Pair(3, 0), state.lastMove)

        state = dropPiece(state, 3)
        assertEquals(Pair(3, 1), state.lastMove)

        state = dropPiece(state, 5)
        assertEquals(Pair(5, 0), state.lastMove)
    }
}
