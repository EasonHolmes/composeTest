package com.example.myapplication.ui.p2048.repository

import android.content.Context
import com.example.myapplication.ui.p2048.domain.Tile2048
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

private const val KEY_SHARED_PREFS = "key_shared_prefs"
private const val KEY_GRID = "key_grid"
private const val KEY_CURRENT_SCORE = "key_current_score"
private const val KEY_BEST_SCORE = "key_best_score"

/**
 * Repository class that persists the current 2048 game to shared preferences.
 */
class Game2048Repository(context: Context) {

    private val sharedPrefs = context.getSharedPreferences(KEY_SHARED_PREFS, Context.MODE_PRIVATE)

    var grid: List<List<Int?>>? = sharedPrefs.getString(KEY_GRID, null)?.let { Gson().fromJson(it) }
        private set

    var currentScore: Int = sharedPrefs.getInt(KEY_CURRENT_SCORE, 0)
        private set

    var bestScore: Int = sharedPrefs.getInt(KEY_BEST_SCORE, 0)
        private set

    fun saveState(grid: List<List<Tile2048?>>, currentScore: Int, bestScore: Int) {
        this.grid = grid.map { tiles -> tiles.map { it?.num } }
        this.currentScore = currentScore
        this.bestScore = bestScore
        sharedPrefs.edit()
            .putString(KEY_GRID, Gson().toJson(this.grid))
            .putInt(KEY_CURRENT_SCORE, currentScore)
            .putInt(KEY_BEST_SCORE, bestScore)
            .apply()
    }
}

private inline fun <reified T> Gson.fromJson(json: String) = fromJson<T>(json, object : TypeToken<T>() {}.type)
