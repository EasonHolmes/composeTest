package com.example.myapplication.ui.p2048.domain

/**
 * Container class describing how a tile has moved within the grid.
 */
data class GridTile2048Movement(val fromGridTile: GridTile2048?, val toGridTile: GridTile2048) {
    companion object {
        /**
         * Creates a [GridTile2048Movement] describing a tile that has been added to the grid.
         */
        fun add(gridTile: GridTile2048): GridTile2048Movement {
            return GridTile2048Movement(null, gridTile)
        }

        /**
         * Creates a [GridTile2048Movement] describing a tile that has shifted to a different location in the grid.
         */
        fun shift(fromGridTile: GridTile2048, toGridTile: GridTile2048): GridTile2048Movement {
            return GridTile2048Movement(fromGridTile, toGridTile)
        }

        /**
         * Creates a [GridTile2048Movement] describing a tile that has not moved in the grid.
         */
        fun noop(gridTile: GridTile2048): GridTile2048Movement {
            return GridTile2048Movement(gridTile, gridTile)
        }
    }
}