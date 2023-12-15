package com.example.myapplication.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.commandiron.spin_wheel_compose.SpinWheel
import com.commandiron.spin_wheel_compose.state.rememberSpinWheelState
import com.example.myapplication.Logutils
import kotlinx.coroutines.launch

//https://github.com/commandiron/SpinWheelCompose
class ZhuanPanActivity : BaseActivity() {
    @Composable
    override fun ContentView() {
        val textList by remember {
            mutableStateOf(
                listOf("Pie 1", "Pie 2", "Pie 3", "Pie 4", "Pie 5", "Pie 6","Pie 7","Pie 8","Pie 9", "Pie 10")
            )
        }

        val state = rememberSpinWheelState(pieCount = textList.size, durationMillis = 2000,)
        val scope = rememberCoroutineScope()

        SpinWheel(
            state = state,
            onClick = { scope.launch { state.animate {pieIndex ->
                Logutils.e("select==="+pieIndex)

            } } }
        ){ pieIndex ->
            Text(text = textList[pieIndex],modifier = Modifier.padding(top = 10.dp))
        }
    }
}