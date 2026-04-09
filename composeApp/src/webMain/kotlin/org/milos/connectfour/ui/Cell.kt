package org.milos.connectfour.ui

import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div
import androidx.compose.runtime.Composable
import org.milos.connectfour.model.Player

@Composable
fun Cell(player: Player, isDropping: Boolean, cellSize: String, fallDistance: Int = 0) {
    Div({
        classes(BoardStyles.cell)
        when (player) {
            Player.ONE -> classes(BoardStyles.cellPlayerOne)
            Player.TWO -> classes(BoardStyles.cellPlayerTwo)
            Player.NONE -> {}
        }
        if (isDropping && player != Player.NONE) {
            classes(BoardStyles.dropping)
        }
        style {
            property("width", cellSize)
            property("height", cellSize)
            if (isDropping && player != Player.NONE) {
                property("--fall-distance", "calc(-$fallDistance * (100% + 6px))")
            }
        }
    })
}
