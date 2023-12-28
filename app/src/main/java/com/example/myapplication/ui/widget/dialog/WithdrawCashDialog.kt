package com.example.myapplication.ui.widget.dialog

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.ViewModel
import com.example.myapplication.R
import com.example.myapplication.ui.widget.GradientProgressbar
import com.example.myapplication.ui.widget.NoDescriptionImage

/**
 * Created by Ethan Cui on 2023/2/16
 */
class WithdrawCashDialog : ComposeBottomDialog<ViewModel>() {
    override fun getViewModel(): Class<ViewModel> {
        return ViewModel::class.java
    }

    @Preview(showBackground = true)
    @Composable
    private fun PreviewUI() {
        ContentView(preview = true)
    }

    @Composable
    override fun ContentView(preview: Boolean) {
        ContentUI(preview)
        Column {
            Text(text = "etst")
            Text(text = "etst")
            Text(text = "etst")
            Text(text = "etst")
            Button(onClick = {
                click.invoke()
            }) {
                Text(text = "close")
            }
            Text(text = "etst")
            Text(text = "etst")
            Text(text = "etst")
            Text(text = "etst")
        }
    }

    private var click: () -> Unit = {}
    public fun setOnclickListener(click: () -> Unit) {
        this.click = click
    }

    @Composable
    private fun ContentUI(preview: Boolean) {
    }
}