package com.example.myapplication.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.*
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.R
import com.example.myapplication.ui.vm.TestViewModel
import com.example.myapplication.ui.widget.GradientButton
import com.example.myapplication.ui.widget.GradientButton2
import com.example.myapplication.ui.widget.createCircularReveal
import kotlinx.coroutines.*

/**
 * Created by Ethan Cui on 2022/10/28
 */
class FoundationActivity : BaseActivity() {
    private val verticalGradientBrush = Brush.horizontalGradient(
        colors = listOf(
            Color(0xFFB9A29C),
            Color(0xff8D6E63),
        )
    )
    val dd = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFFFC638),
            Color(0xFFFEA41D)
        )
    )
    val gradient =
        Brush.horizontalGradient(listOf(Color(0xFFC6E270), Color(0xFF00BEB2)))

    @Composable
    override fun ContentView() {
        TestButtons()
    }

    override fun getActTtitle(): String {
        return "Foundation"
    }

    //委托形式初始化viewmodel 同页面下的Composeable的viewmodel如果已经初始化会返回同一个
    @OptIn(ExperimentalLifecycleComposeApi::class)
    @Composable
    fun TestButtons(viewmodel: TestViewModel = viewModel()) {
        Log.e("ethan", "TestButtons===")
        val stateData2 by viewmodel.uiEvent.collectAsStateWithLifecycle()

        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center) {
            Button(modifier = Modifier.align(alignment = Alignment.CenterHorizontally), onClick = {
                TestDialog().show(this@FoundationActivity.supportFragmentManager, "dialog")
            }) {
                Text(text = "dialog \n mutableStateOf、 eventLivedata ChangeUI")
            }
            // Holds state
            GradientButton(
                elevation = ButtonDefaults.elevation(
                    defaultElevation = 28.dp,
                    pressedElevation = 16.dp
                ),
                gradient = gradient,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                onClick = {
                    viewmodel.changeData()
                }
            ) {
                Text(text = "渐变button-fillMaxWidth==${stateData2.content}")
            }
            GradientButton2(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                gradient = gradient,
                onClick = {}
            ) {
                Text(text = "使用系统button增加渐变属性，渐变button - Wrap Width")
            }
            CircularRevealAnima()
            MaterialButton()

            // widget.ImageView
//                AndroidView(factory = { ctx ->
//                    //  Initialize a View or View hierarchy here
//                    TextView(ctx).apply {
//                        layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
//                    }
//                }, update = {
//                    // Update TextView with the current state value
//                    it.text = "You have clicked the buttons: " + state.value.toString() + " times"
//                })
        }
    }

    @Composable
    fun MaterialButton(viewmodel: TestViewModel = viewModel()) {
        Log.e("ethan", "TestButtons===22")
        val stateData = viewmodel.uiViewEvent.observeAsState()

//        androidx.compose.material3.Button(onClick = {
//            viewmodel.changeData()
//        }) {
//            androidx.compose.material3.Text(
//                text = stateData.value?.exampleUiData?.content
//                    ?: "click me singleLiveData ViewModel changeUI"
//            )
//        }
    }
    @Composable
    fun CircularRevealAnima(){
        Column {
            LocalView.current.createCircularReveal()//把当前的compose转成view，也就是整个页面的
            Image(
                painter = painterResource(id = R.mipmap.jinzhu_icon),
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp),
                contentDescription = "",
            )
        }
    }


    @Composable
    fun testEffect2(viewmodel: TestViewModel = viewModel()) {
        var content = remember {
            mutableStateOf("context")
        }
        val scop = rememberCoroutineScope()
        Column {
            Text(text = content.value)
            val delay = TestDelay()
            DisposableEffect(scop) {
                scop.launch {
                    delay.delayTestLaunch {
                        content.value = it//退出时协程关闭 方法体内的ui 已销毁
                        Log.e("ethan", "delayTest")
                    }
                }
                onDispose {
                    Log.e("ethan", "onDispose")
                    scop.cancel()
                    Log.e("ethan", "onDispose" + scop.isActive)
                }
            }
        }
    }

    @SuppressLint("CoroutineCreationDuringComposition")
    @Composable
    fun testEffect() {
        val inputText = remember { mutableStateOf("") }
        val scope = rememberCoroutineScope()
        Column(modifier = Modifier.padding(16.dp)) {
//            scope.launch(Dispatchers.Main) {
//                for (i in 0..10000) {
//                    delay(1000)
//                    Log.e("ethan", "i:$i")
//                }
//            }
//            DisposableEffect(key1 = inputText.value) {////监听数据变化 会回调这里 退出后会关闭协程 并且有dispose 方法可以在退出页面里取消些东西
//                Log.e("ethan", "on spose")
//                onDispose {
//                    Log.e("ethan", "onDispose")
//                }
//            }
            LaunchedEffect(key1 = inputText.value, block = { //监听数据变化 会回调这里 退出后会关闭协程
                Log.e("ethan", "on spose33333")
                this.launch(Dispatchers.Main) {
                    for (i in 0..10000) {
                        delay(1000)
                        Log.e("ethan", "i:$i")
                    }
                }
            })

            Text(
                text = "Hello",
                modifier = Modifier.padding(bottom = 8.dp),
                style = MaterialTheme.typography.h5,
            )
            OutlinedTextField(
                value = inputText.value,
                onValueChange = { inputText.value = it },
                label = { Text(text = "Name") },
            )
        }
    }

    @Composable
    fun ConstraintLayoutContent() {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            // Create references for the composables to constrain
            val (button, textTitle, textContent) = createRefs()
            Button(onClick = {
                // Assign reference "button" to the Button composable
            }, modifier = Modifier.constrainAs(button) {
                top.linkTo(parent.top, margin = 16.dp)
                bottom.linkTo(parent.bottom, margin = 16.dp)
                start.linkTo(parent.start, margin = 16.dp)
                end.linkTo(parent.end, margin = 16.dp)
            }) {
                Text(text = "跳转到第二页")
            }

            TextButton(onClick = {
            }, Modifier.constrainAs(textTitle) {
                top.linkTo(button.bottom, margin = 16.dp)
                end.linkTo(parent.end, margin = 16.dp)
                start.linkTo(parent.start, margin = 16.dp)
            }) {
                Text(text = "跳转到第三页")
            }

            TextButton(onClick = {
            }, Modifier.constrainAs(textContent) {
                top.linkTo(textTitle.bottom, margin = 16.dp)
                end.linkTo(parent.end, margin = 16.dp)
                start.linkTo(parent.start, margin = 16.dp)
            }) {
                Text(text = "我该跳哪里呢？")
            }
        }
    }
}