package org.milos.connectfour.ui

import org.jetbrains.compose.web.css.*

object AppStyles : StyleSheet() {

    val appContainer by style {
        display(DisplayStyle.Flex)
        flexDirection(FlexDirection.Column)
        alignItems(AlignItems.Center)
        property("min-height", "100vh")
        property("font-family", "'Segoe UI', system-ui, sans-serif")
        backgroundColor(Color("#0f0f23"))
        color(Color("#e0e0e0"))
        padding(16.px)
        property("box-sizing", "border-box")
    }

    val statusBar by style {
        property("font-size", "1.4rem")
        property("font-weight", "600")
        padding(12.px)
        textAlign("center")
    }

    val playerOneText by style {
        color(Color("#e74c3c"))
    }

    val playerTwoText by style {
        color(Color("#f1c40f"))
    }

    val drawText by style {
        color(Color("#95a5a6"))
    }

    val button by style {
        padding(10.px, 24.px)
        property("font-size", "1rem")
        property("font-weight", "600")
        border(0.px)
        borderRadius(8.px)
        cursor("pointer")
        backgroundColor(Color("#3498db"))
        color(Color("#ffffff"))
        property("transition", "background-color 0.2s ease, transform 0.1s ease")

        self + hover style {
            backgroundColor(Color("#2980b9"))
        }

        self + active style {
            property("transform", "scale(0.97)")
        }
    }

    val buttonDisabled by style {
        backgroundColor(Color("#555"))
        cursor("default")
        property("opacity", "0.6")

        self + hover style {
            backgroundColor(Color("#555"))
        }
    }

    val configForm by style {
        display(DisplayStyle.Flex)
        flexDirection(FlexDirection.Column)
        alignItems(AlignItems.Center)
        property("gap", "16px")
        padding(24.px)
        backgroundColor(Color("#1a1a3e"))
        borderRadius(12.px)
        property("box-shadow", "0 4px 16px rgba(0,0,0,0.3)")
        property("min-width", "280px")
    }

    val configField by style {
        display(DisplayStyle.Flex)
        flexDirection(FlexDirection.Column)
        property("gap", "4px")
        property("width", "100%")
    }

    val configLabel by style {
        property("font-size", "0.85rem")
        color(Color("#aaa"))
        property("text-transform", "uppercase")
        property("letter-spacing", "0.05em")
    }

    val configInput by style {
        padding(8.px, 12.px)
        property("font-size", "1rem")
        border(1.px, LineStyle.Solid, Color("#444"))
        borderRadius(6.px)
        backgroundColor(Color("#0f0f23"))
        color(Color("#e0e0e0"))
        property("width", "100%")
        property("box-sizing", "border-box")
        property("outline", "none")

        self + focus style {
            property("border-color", "#3498db")
        }
    }

    val errorText by style {
        color(Color("#e74c3c"))
        property("font-size", "0.8rem")
    }

    val controls by style {
        display(DisplayStyle.Flex)
        property("gap", "12px")
        padding(12.px)
    }

    val title by style {
        property("font-size", "2rem")
        property("font-weight", "700")
        property("margin-bottom", "8px")
        color(Color("#ffffff"))
    }
}
