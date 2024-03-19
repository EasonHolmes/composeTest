package com.example.myapplication.ui.p2048.ui

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun GameDialog(
    modifier: Modifier = Modifier,
    title: String,
    message: String,
    onConfirmListener: () -> Unit,
    onDismissListener: () -> Unit,
) {
    val ok = "ok"
    val cancel = "cancel"
    AlertDialog(
        modifier = modifier,
        title = { Text(text = title) },
        text = { Text(text = message) },
        confirmButton = { TextButton(onClick = { onConfirmListener() }) { Text(ok) } },
        dismissButton = { TextButton(onClick = { onDismissListener() }) { Text(cancel) } },
        onDismissRequest = { onDismissListener() },
    )
}
