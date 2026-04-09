package org.milos.connectfour.ui

import androidx.compose.runtime.*
import org.jetbrains.compose.web.attributes.disabled
import org.jetbrains.compose.web.attributes.max
import org.jetbrains.compose.web.attributes.min
import org.jetbrains.compose.web.dom.*
import org.jetbrains.compose.web.attributes.InputType
import org.milos.connectfour.model.GameConfig

@Composable
fun ConfigScreen(onStartGame: (GameConfig) -> Unit) {
    var cols by remember { mutableStateOf(7) }
    var rows by remember { mutableStateOf(6) }
    var winCondition by remember { mutableStateOf(4) }

    val colsValid = cols in 4..20
    val rowsValid = rows in 4..20
    val winValid = winCondition in 3..10 && winCondition <= minOf(cols, rows)
    val allValid = colsValid && rowsValid && winValid

    Div({ classes(AppStyles.appContainer) }) {
        Div({ classes(AppStyles.title) }) { Text("Connect Four") }

        Div({ classes(AppStyles.configForm) }) {
            ConfigField("Columns (4–20)", cols, { cols = it }, colsValid,
                if (!colsValid) "Must be between 4 and 20" else null, minValue = 4)

            ConfigField("Rows (4–20)", rows, { rows = it }, rowsValid,
                if (!rowsValid) "Must be between 4 and 20" else null, minValue = 4)

            ConfigField(
                "Win Condition (3–10)", winCondition, { winCondition = it }, winValid,
                when {
                    winCondition !in 3..10 -> "Must be between 3 and 10"
                    winCondition > minOf(cols, rows) -> "Must be ≤ min(cols, rows) = ${minOf(cols, rows)}"
                    else -> null
                },
                minValue = 3, maxValue = 10
            )

            Button({
                classes(AppStyles.button)
                if (!allValid) {
                    classes(AppStyles.buttonDisabled)
                    disabled()
                }
                onClick {
                    if (allValid) onStartGame(GameConfig(cols, rows, winCondition))
                }
            }) {
                Text("Start Game")
            }
        }
    }
}

@Composable
private fun ConfigField(
    label: String,
    value: Int,
    onValueChange: (Int) -> Unit,
    isValid: Boolean,
    errorMessage: String?,
    minValue: Int = 4,
    maxValue: Int = 20
) {
    Div({ classes(AppStyles.configField) }) {
        Label(attrs = { classes(AppStyles.configLabel) }) { Text(label) }
        Input(InputType.Number) {
            classes(AppStyles.configInput)
            value(value.toString())
            min(minValue.toString())
            max(maxValue.toString())
            onInput { event ->
                event.value?.toString()?.toIntOrNull()?.let { onValueChange(it) }
            }
        }
        if (!isValid && errorMessage != null) {
            Div({ classes(AppStyles.errorText) }) { Text(errorMessage) }
        }
    }
}
