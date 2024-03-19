package com.example.myapplication.ui.widget

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp


@Composable
fun CheckBoxGroup(
    checkedKey: String,
    strs: List<String>,
    textStyle: TextStyle,
    checkedColor: Color?,
    onChanged: (key: String) -> Unit
) {
    var currentChecked by remember {
        mutableStateOf(checkedKey)
    }
    strs.forEach { str ->
        Row(
            Modifier.clickable(indication = null,
                interactionSource = remember {
                    MutableInteractionSource()
                }, onClick = {
                    currentChecked = str
                    onChanged(str)
                }
            ), verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                modifier = Modifier.size(width = 30.dp, height = 30.dp),
                checked = currentChecked == str,
                onCheckedChange = { bool ->
                    currentChecked = str
                    onChanged(str)
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = checkedColor ?: MaterialTheme.colors.secondary
                )
            )
            Text(str, style = textStyle)
        }
    }

}

