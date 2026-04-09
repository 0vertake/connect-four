package org.milos.connectfour.storage

import kotlinx.browser.localStorage
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.milos.connectfour.model.GameState

object GameStorage {

    private const val KEY = "connect_four_state"

    private val json = Json { ignoreUnknownKeys = true }

    fun save(state: GameState) {
        try {
            localStorage.setItem(KEY, json.encodeToString(state))
        } catch (_: Exception) {
            // silently ignore storage failures
        }
    }

    fun load(): GameState? =
        try {
            localStorage.getItem(KEY)?.let { json.decodeFromString<GameState>(it) }
        } catch (_: Exception) {
            null
        }

    fun clear() {
        localStorage.removeItem(KEY)
    }
}
