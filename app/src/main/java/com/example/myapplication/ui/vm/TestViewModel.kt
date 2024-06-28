package com.example.myapplication.ui.vm

import androidx.compose.runtime.ComposeCompilerApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow


/**
 * Created by Ethan Cui on 2022/11/14
 */
class TestViewModel : ViewModel() {
    val uiViewEvent: SingleLivedata<ExampleUiState> = SingleLivedata()
    val uiEvent = MutableStateFlow(ExampleUiData("content1111"))
    fun changeData(int: Int = 0) {
        uiViewEvent.value = ExampleUiState(exampleUiData = ExampleUiData("testestestset" + int))
        uiEvent.value = ExampleUiData("testestestset")
    }

    var ttt = "212"
}

class PreviewViewmodel : ViewModel() {
    private val uiViewEvent: MutableStateFlow<ExampleUiState2.StateData> =
        MutableStateFlow(ExampleUiState2.StateData(ExampleUiData()))

    fun uiViewEvent() = uiViewEvent

    fun changeData() {
        uiViewEvent.value = ExampleUiState2.StateData(ExampleUiData("testtttttt"))
    }
}


class TestViewModel2 : InlandBaseViewModel<ExampleUiState2>() {
    var stateData = mutableStateOf(ExampleUiData("click me changeUi"))

    fun changeData2() {
        modelEvnet.setEvent(ExampleUiState2.StateData(ExampleUiData("datetsetset")))
        stateData.value = ExampleUiData("testsetsestet")
    }
}

sealed class ExampleUiState2 {
    data class StateData(val response: ExampleUiData) : ExampleUiState2()
}

data class ExampleUiState(
    var loading: Boolean = false,
    var testContent: String = "click me changeUi",
    var exampleUiData: ExampleUiData = ExampleUiData("no data")
)

data class ExampleUiData(
    var content: String = "click me changeUi"
)