package com.example.myapplication.ui.p2048.viewmodel

import android.content.Context
import androidx.annotation.IntRange
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.myapplication.ui.p2048.domain.Cell2048
import com.example.myapplication.ui.p2048.domain.Direction2048
import com.example.myapplication.ui.p2048.domain.GridTile2048
import com.example.myapplication.ui.p2048.domain.GridTile2048Movement
import com.example.myapplication.ui.p2048.domain.Tile2048
import com.example.myapplication.ui.p2048.repository.Game2048Repository
import java.lang.Math.floorMod
import kotlin.math.max

const val GRID_SIZE = 4
private const val NUM_INITIAL_TILES = 2
private val EMPTY_GRID = (0 until GRID_SIZE).map { arrayOfNulls<Tile2048?>(GRID_SIZE).toList() }

/**
 * View model class that contains the logic that powers the 2048 game.
 */
class GameViewModel constructor(private val context: Context) {
    val game2048Repository = Game2048Repository(context)

    private var grid: List<List<Tile2048?>> = EMPTY_GRID
    var gridTileMovements by mutableStateOf<List<GridTile2048Movement>>(listOf())
        private set
    var currentScore by mutableStateOf(game2048Repository.currentScore)
        private set
    var bestScore by mutableStateOf(game2048Repository.bestScore)
        private set
    var isGameOver by mutableStateOf(false)
        private set
    var moveCount by mutableStateOf(0)
        private set

    init {
        restore()
    }

    fun restore() {
        val savedGrid = game2048Repository.grid
        if (savedGrid == null) {
            startNewGame()
        }
        else {
            // Restore a previously saved game.
            grid = savedGrid.map { tiles -> tiles.map { if (it == null) null else Tile2048(it) } }
            gridTileMovements = savedGrid.flatMapIndexed { row, tiles ->
                tiles.mapIndexed { col, it ->
                    if (it == null) null else GridTile2048Movement.noop(
                        GridTile2048(
                            Cell2048(row, col),
                            Tile2048(it)
                        )
                    )
                }
            }.filterNotNull()
            currentScore = game2048Repository.currentScore
            bestScore = game2048Repository.bestScore
            isGameOver = checkIsGameOver(grid)
        }
    }

    fun startNewGame() {
        gridTileMovements = (0 until NUM_INITIAL_TILES).mapNotNull { createRandomAddedTile(
            EMPTY_GRID
        ) }
        val addedGridTiles = gridTileMovements.map { it.toGridTile }
        grid = EMPTY_GRID.map { row, col, _ -> addedGridTiles.find { row == it.cell.row && col == it.cell.col }?.tile2048 }
        currentScore = 0
        isGameOver = false
        moveCount = 0
        game2048Repository.saveState(grid, currentScore, bestScore)
    }

    fun move(direction: Direction2048) {
        var (updatedGrid, updatedGridTileMovements) = makeMove(grid, direction)

        if (!hasGridChanged(updatedGridTileMovements)) {
            // No tiles were moved.
            return
        }
        // Save status.
        game2048Repository.saveState(grid, currentScore, bestScore)

        // Increment the score.
        val scoreIncrement = updatedGridTileMovements
            .filter { it.fromGridTile == null }
            .sumOf { it.toGridTile.tile2048.num }
        currentScore += scoreIncrement
        bestScore = max(bestScore, currentScore)

        // Attempt to add a new tile to the grid.
        updatedGridTileMovements = updatedGridTileMovements.toMutableList()
        val addedTileMovement = createRandomAddedTile(updatedGrid)
        if (addedTileMovement != null) {
            val (cell, tile) = addedTileMovement.toGridTile
            updatedGrid = updatedGrid.map { r, c, it -> if (cell.row == r && cell.col == c) tile else it }
            updatedGridTileMovements.add(addedTileMovement)
        }

        grid = updatedGrid
        gridTileMovements = updatedGridTileMovements.sortedWith { a, _ -> if (a.fromGridTile == null) 1 else -1 }
        isGameOver = checkIsGameOver(grid)
        moveCount++
    }
}

private fun createRandomAddedTile(grid: List<List<Tile2048?>>): GridTile2048Movement? {
    val emptyCells = grid.flatMapIndexed { row, tiles ->
        tiles.mapIndexed { col, it -> if (it == null) Cell2048(row, col) else null }.filterNotNull()
    }
    val emptyCell = emptyCells.getOrNull(emptyCells.indices.random()) ?: return null
    return GridTile2048Movement.add(GridTile2048(emptyCell, if (Math.random() < 0.9f) Tile2048(2) else Tile2048(4)))
}

private fun makeMove(grid: List<List<Tile2048?>>, direction: Direction2048): Pair<List<List<Tile2048?>>, List<GridTile2048Movement>> {
    val numRotations = when (direction) {
        Direction2048.WEST -> 0
        Direction2048.SOUTH -> 1
        Direction2048.EAST -> 2
        Direction2048.NORTH -> 3
    }

    // Rotate the grid so that we can process it as if the user has swiped their
    // finger from right to left.
    var updatedGrid = grid.rotate(numRotations)

    val gridTileMovements = mutableListOf<GridTile2048Movement>()

    updatedGrid = updatedGrid.mapIndexed { currentRowIndex, _ ->
        val tiles = updatedGrid[currentRowIndex].toMutableList()
        var lastSeenTileIndex: Int? = null
        var lastSeenEmptyIndex: Int? = null
        for (currentColIndex in tiles.indices) {
            val currentTile = tiles[currentColIndex]
            if (currentTile == null) {
                // We are looking at an empty cell in the grid.
                if (lastSeenEmptyIndex == null) {
                    // Keep track of the first empty index we find.
                    lastSeenEmptyIndex = currentColIndex
                }
                continue
            }

            // Otherwise, we have encountered a tile that could either be shifted,
            // merged, or not moved at all.
            val currentGridTile = GridTile2048(getRotatedCellAt(currentRowIndex, currentColIndex, numRotations), currentTile)

            if (lastSeenTileIndex == null) {
                // This is the first tile in the list that we've found.
                if (lastSeenEmptyIndex == null) {
                    // Keep the tile at its same location.
                    gridTileMovements.add(GridTile2048Movement.noop(currentGridTile))
                    lastSeenTileIndex = currentColIndex
                } else {
                    // Shift the tile to the location of the furthest empty cell in the list.
                    val targetCell = getRotatedCellAt(currentRowIndex, lastSeenEmptyIndex, numRotations)
                    val targetGridTile = GridTile2048(targetCell, currentTile)
                    gridTileMovements.add(GridTile2048Movement.shift(currentGridTile, targetGridTile))

                    tiles[lastSeenEmptyIndex] = currentTile
                    tiles[currentColIndex] = null
                    lastSeenTileIndex = lastSeenEmptyIndex
                    lastSeenEmptyIndex++
                }
            } else {
                // There is a previous tile in the list that we need to process.
                if (tiles[lastSeenTileIndex]!!.num == currentTile.num) {
                    // Shift the tile to the location where it will be merged.
                    val targetCell = getRotatedCellAt(currentRowIndex, lastSeenTileIndex, numRotations)
                    gridTileMovements.add(GridTile2048Movement.shift(currentGridTile, GridTile2048(targetCell, currentTile)))

                    // Merge the current tile with the previous tile.
                    val addedTile = currentTile * 2
                    gridTileMovements.add(GridTile2048Movement.add(GridTile2048(targetCell, addedTile)))

                    tiles[lastSeenTileIndex] = addedTile
                    tiles[currentColIndex] = null
                    lastSeenTileIndex = null
                    if (lastSeenEmptyIndex == null) {
                        lastSeenEmptyIndex = currentColIndex
                    }
                } else {
                    if (lastSeenEmptyIndex == null) {
                        // Keep the tile at its same location.
                        gridTileMovements.add(GridTile2048Movement.noop(currentGridTile))
                    } else {
                        // Shift the current tile towards the previous tile.
                        val targetCell = getRotatedCellAt(currentRowIndex, lastSeenEmptyIndex, numRotations)
                        val targetGridTile = GridTile2048(targetCell, currentTile)
                        gridTileMovements.add(GridTile2048Movement.shift(currentGridTile, targetGridTile))

                        tiles[lastSeenEmptyIndex] = currentTile
                        tiles[currentColIndex] = null
                        lastSeenEmptyIndex++
                    }
                    lastSeenTileIndex++
                }
            }
        }
        tiles
    }

    // Rotate the grid back to its original state.
    updatedGrid = updatedGrid.rotate(floorMod(-numRotations, GRID_SIZE))

    return Pair(updatedGrid, gridTileMovements)
}

private fun <T> List<List<T>>.rotate(@IntRange(from = 0, to = 3) numRotations: Int): List<List<T>> {
    return map { row, col, _ ->
        val (rotatedRow, rotatedCol) = getRotatedCellAt(row, col, numRotations)
        this[rotatedRow][rotatedCol]
    }
}

private fun getRotatedCellAt(row: Int, col: Int, @IntRange(from = 0, to = 3) numRotations: Int): Cell2048 {
    return when (numRotations) {
        0 -> Cell2048(row, col)
        1 -> Cell2048(GRID_SIZE - 1 - col, row)
        2 -> Cell2048(GRID_SIZE - 1 - row, GRID_SIZE - 1 - col)
        3 -> Cell2048(col, GRID_SIZE - 1 - row)
        else -> throw IllegalArgumentException("numRotations must be an integer in [0,3]")
    }
}

private fun <T> List<List<T>>.map(transform: (row: Int, col: Int, T) -> T): List<List<T>> {
    return mapIndexed { row, rowTiles -> rowTiles.mapIndexed { col, it -> transform(row, col, it) } }
}

private fun checkIsGameOver(grid: List<List<Tile2048?>>): Boolean {
    // The game is over if no tiles can be moved in any of the 4 directions.
    return Direction2048.values().none { hasGridChanged(makeMove(grid, it).second) }
}

private fun hasGridChanged(gridTileMovements: List<GridTile2048Movement>): Boolean {
    // The grid has changed if any of the tiles have moved to a different location.
    return gridTileMovements.any {
        val (fromTile, toTile) = it
        fromTile == null || fromTile.cell != toTile.cell
    }
}
