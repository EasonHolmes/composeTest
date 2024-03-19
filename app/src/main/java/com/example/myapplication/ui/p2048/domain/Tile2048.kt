package com.example.myapplication.ui.p2048.domain

/**
 * Container class that wraps a number and a unique ID for use in the grid.
 */
data class Tile2048 constructor(val num: Int, val id: Int) {
    companion object {
        // We assign each tile a unique ID and use it to efficiently
        // animate tile objects within the compose UI.
        private var tileIdCounter = 0
    }

    constructor(num: Int) : this(num, tileIdCounter++)

    operator fun times(operand: Int): Tile2048 = Tile2048(num * operand)
}

