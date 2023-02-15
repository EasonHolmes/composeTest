package com.example.myapplication.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.myapplication.ui.vm.*
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * Created by Ethan Cui on 2023/2/15
 * https://medium.com/geekculture/one-of-the-ways-to-fix-the-jetpack-compose-preview-error-viewmodels-creation-is-not-supported-62826674e474#id_token=eyJhbGciOiJSUzI1NiIsImtpZCI6IjU5NjJlN2EwNTljN2Y1YzBjMGQ1NmNiYWQ1MWZlNjRjZWVjYTY3YzYiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJuYmYiOjE2NzY0MzEwNjEsImF1ZCI6IjIxNjI5NjAzNTgzNC1rMWs2cWUwNjBzMnRwMmEyamFtNGxqZGNtczAwc3R0Zy5hcHBzLmdvb2dsZXVzZXJjb250ZW50LmNvbSIsInN1YiI6IjExMzY0Mzk0MTAwNjQ2NzM5MDI4OCIsImVtYWlsIjoiaG9sbWVzZWFzb25AZ21haWwuY29tIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsImF6cCI6IjIxNjI5NjAzNTgzNC1rMWs2cWUwNjBzMnRwMmEyamFtNGxqZGNtczAwc3R0Zy5hcHBzLmdvb2dsZXVzZXJjb250ZW50LmNvbSIsIm5hbWUiOiJjdWkgRWFzb24iLCJwaWN0dXJlIjoiaHR0cHM6Ly9saDMuZ29vZ2xldXNlcmNvbnRlbnQuY29tL2EvQUVkRlRwN1B2QUlFN2dBblItTU41VFVyWThRMmFSeEo2MEV6bzZ2cGZOZ0k9czk2LWMiLCJnaXZlbl9uYW1lIjoiY3VpIiwiZmFtaWx5X25hbWUiOiJFYXNvbiIsImlhdCI6MTY3NjQzMTM2MSwiZXhwIjoxNjc2NDM0OTYxLCJqdGkiOiI0OGU3ZGIxYjJmNDFjMTZjMjU4MjdmN2Q2NzU4NDdkYmE0NGMyZDUwIn0.NC_hGtIS6u2H2fzFxid9Yn7FG2G16pP9kJ7Sj9renshj0R5w4Q-uIMtVf5JRLHJtEyzH7dTrMkxHwEVw7KgZTUYbTAJjp_AiAS-nvs2dLKfb2X_qNAbC1U-2R2y3ttdfaisMfF0ffvJ2iao0aaF7DRHolLfbc6KWvxqqNJvINLQkV-nserxNRiLOfzdIJKYVCMXCOclMC5k_jbtAdKNxfmOhpH0T8QxYWnSxEzyUzkTN3UcR2aLJ8D0BdUtPXhUxdLGeWbjbg_fmL1WosJAi5ZL6kqOG55yGvk1qVb4CJXZBWrGOVxhnVM1ovxiLe9ynjq3gkWwEFLLoavhtjgwMFA
 */
class PreviewByViewmodelActivity : BaseActivity() {
    private val viewmodel by lazy {
        ViewModelProvider(this)[PreviewViewmodel::class.java]
    }

    @Preview(showSystemUi = true)
    @Composable
    fun PreviewUI() {
        ContentUi()
    }

    @Composable
    override fun ContentView() {
        ContentUi(viewmodel.uiViewEvent())
    }

    @OptIn(ExperimentalLifecycleComposeApi::class)
    @Composable
    private fun ContentUi(
        uiEvent: MutableStateFlow<ExampleUiState2.StateData> = MutableStateFlow(
            ExampleUiState2.StateData(
                ExampleUiData()
            )
        ),
    ) {
        val stateData by uiEvent.collectAsStateWithLifecycle()

        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center) {
            // Holds state
            Button(
                elevation = ButtonDefaults.elevation(
                    defaultElevation = 28.dp,
                    pressedElevation = 16.dp
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                onClick = {
                    changeData()
                }
            ) {
                Text(
                    text = "prewview下的Viewmodel使用：\n" +
                            "Compose方法中不直接使用Viewmodel\n\n" +
                            "把viewmodel的方法放到另外的方法\n\n" +
                            "Compose方法中仅使用Viewmodel里的UIState来监听就可以\n\n" +
                            "==${stateData.response.content}"
                )
            }
        }
    }

    private fun changeData() {
        viewmodel.changeData()
    }
}