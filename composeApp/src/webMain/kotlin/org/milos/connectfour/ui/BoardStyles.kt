package org.milos.connectfour.ui

import org.jetbrains.compose.web.css.*

object BoardStyles : StyleSheet() {

    val fall by keyframes {
        from { property("transform", "translateY(var(--fall-distance, -400%))") }
        to { property("transform", "translateY(0)") }
    }

    val board by style {
        property("display", "inline-grid")
        property("gap", "6px")
        padding(10.px)
        backgroundColor(Color("#2c3e9b"))
        borderRadius(12.px)
        property("box-shadow", "0 8px 32px rgba(0,0,0,0.4)")
    }

    val column by style {
        display(DisplayStyle.Flex)
        flexDirection(FlexDirection.Column)
        property("gap", "6px")
        cursor("pointer")
        borderRadius(8.px)
        property("transition", "background-color 0.15s ease")

        self + hover style {
            backgroundColor(Color("rgba(255, 255, 255, 0.08)"))
        }
    }

    val columnDisabled by style {
        cursor("default")

        self + hover style {
            backgroundColor(Color.transparent)
        }
    }

    val cell by style {
        borderRadius(50.percent)
        backgroundColor(Color("#1a1a3e"))
        property("transition", "background-color 0.15s ease")
        property("box-shadow", "inset 0 2px 6px rgba(0,0,0,0.5)")
    }

    val cellPlayerOne by style {
        backgroundColor(Color("#e74c3c"))
        property("box-shadow", "inset 0 -2px 4px rgba(0,0,0,0.3), 0 0 8px rgba(231,76,60,0.4)")
    }

    val cellPlayerTwo by style {
        backgroundColor(Color("#f1c40f"))
        property("box-shadow", "inset 0 -2px 4px rgba(0,0,0,0.3), 0 0 8px rgba(241,196,15,0.4)")
    }

    val dropping by style {
        property("animation-name", fall.name)
        property("animation-duration", "300ms")
        property("animation-timing-function", "ease-in")
        property("animation-fill-mode", "both")
    }
}
