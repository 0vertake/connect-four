package org.milos.connectfour.model

import kotlinx.serialization.Serializable

@Serializable
data class GameConfig(
    val cols: Int = 7,
    val rows: Int = 6,
    val winCondition: Int = 4
)

@Serializable
sealed class GameStatus {
    @Serializable
    data object Ongoing : GameStatus()

    @Serializable
    data class Won(val winner: Player) : GameStatus()

    @Serializable
    data object Draw : GameStatus()
}

@Serializable
data class GameState(
    val config: GameConfig,
    val board: List<List<Player>>,
    val currentPlayer: Player,
    val status: GameStatus,
    val lastMove: Pair<Int, Int>? = null
)
