package org.milos.connectfour

import org.jetbrains.compose.web.css.Style
import org.jetbrains.compose.web.renderComposable
import org.milos.connectfour.ui.App
import org.milos.connectfour.ui.AppStyles
import org.milos.connectfour.ui.BoardStyles

fun main() {
    renderComposable(rootElementId = "root") {
        Style(AppStyles)
        Style(BoardStyles)
        App()
    }
}
