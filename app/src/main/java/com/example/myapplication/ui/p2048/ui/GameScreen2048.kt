package com.example.myapplication.ui.p2048.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import com.example.myapplication.ui.p2048.viewmodel.GameViewModel

@Composable
fun GameScreen(
    gameViewModel: GameViewModel
) {
    AppTheme {
        var shouldShowNewGameDialog by remember { mutableStateOf(false) }
        val orientation = LocalConfiguration.current.orientation
        GameUi(
            modifier = Modifier.fillMaxSize(),
            gridTileMovements = gameViewModel.gridTileMovements,
            currentScore = gameViewModel.currentScore,
            bestScore = gameViewModel.bestScore,
            moveCount = gameViewModel.moveCount,
            isPortrait = orientation == Configuration.ORIENTATION_PORTRAIT,
            onSwipeListener = { direction -> gameViewModel.move(direction) },
            onNewGameRequested = { shouldShowNewGameDialog = true },
            onRestoreGameRequested = { gameViewModel.restore() },
        )

        if (gameViewModel.isGameOver) {
            GameDialog(
                title = "game_over",
                message = "msg_game_over_body",
                onConfirmListener = { gameViewModel.startNewGame() },
                onDismissListener = { gameViewModel.restore() },
            )
        } else if (shouldShowNewGameDialog) {
            GameDialog(
                title = "msg_start_new_game",
                message = "msg_start_new_game_body",
                onConfirmListener = {
                    gameViewModel.startNewGame()
                    shouldShowNewGameDialog = false
                },
                onDismissListener = { shouldShowNewGameDialog = false },
            )
        }
    }
}
