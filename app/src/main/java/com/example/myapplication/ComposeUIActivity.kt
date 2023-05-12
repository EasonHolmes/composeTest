package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.media.AudioManager
import android.os.Environment
import android.provider.MediaStore
import android.provider.UserDictionary
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.ui.*
import com.example.myapplication.ui.mytheme.ChangeColorApplicationTheme
import com.example.myapplication.ui.mytheme.ColorTheme
import com.example.myapplication.ui.pintu.GameActivity
import com.example.myapplication.ui.utils.TimerLifecycle
import com.example.myapplication.ui.vm.ExampleUiState
import com.example.myapplication.ui.vm.TestViewModel
import org.json.JSONArray
import java.io.File
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Locale


/**
 * Created by Ethan Cui on 2022/3/11
 */
enum class JumpEntity(val value: String) {
    ANIMATION("animation"),
    FOUNDATION("foundation"),
    MOTIONLAYOUT("motionLayout"),
    COLLAPSING("Collapsing视差"),
    BOTTOM_BAR("Bottombar"),
    NAVIGATION("Navigation"),
    PREVIEW_VIEWMODE("Viewmodel在Preview中使用"),
    GAME("拼图游戏"),
    ANIMATION2("animation2")
}

class ComposeUIActivity : BaseActivity() {
    val listData = arrayListOf<JumpEntity>(
        JumpEntity.ANIMATION,
        JumpEntity.FOUNDATION,
        JumpEntity.MOTIONLAYOUT,
        JumpEntity.COLLAPSING,
        JumpEntity.BOTTOM_BAR,
        JumpEntity.NAVIGATION,
        JumpEntity.PREVIEW_VIEWMODE,
        JumpEntity.GAME,
        JumpEntity.ANIMATION2
    )

    companion object {
        val ETAG = "ethan"
    }


    private val mViewmodel by lazy {
        Log.e("ethan", "iiiiii")
        ViewModelProvider(this)[TestViewModel::class.java]
    }

    @SuppressLint("CoroutineCreationDuringComposition")
    @Composable
    override fun ContentView() {
        var changeColor by remember {
            mutableStateOf(ColorTheme.WHITE)
        }
        Column {
            //还有lightDarkTheme根据主题变化
            ChangeColorApplicationTheme(changeColor) {
                Button(onClick = {
                    changeColor = ColorTheme.BLACK
                }) {
                    Text(text = "改变主题")
                }
                ListUI()
            }
        }
    }
    private fun check() {
        val am: AudioManager = getSystemService(AUDIO_SERVICE) as AudioManager
        am.setStreamVolume(AudioManager.STREAM_MUSIC, 0, AudioManager.FLAG_PLAY_SOUND)
    }

    fun countDownTimeUtil(
        millis: Long,
        lifecycleOwner: LifecycleOwner,
        tickCallback: (millisUntilFinished: Long) -> Unit = {},
        finishCallback: () -> Unit = {},
        countDownInterval: Long = 1000
    ): TimerLifecycle {
        val timer = object : TimerLifecycle(millis, countDownInterval) {
            override fun onTick(millisUntilFinished: Long) {
                tickCallback(millisUntilFinished)
            }

            override fun onFinish() {
                this.cancel()
                finishCallback()
            }

//            override fun destroy() {
//                finish()
//            }
        }
        lifecycleOwner.lifecycle.addObserver(timer)
        timer.start()
        return timer
    }

    override fun onResume() {
        super.onResume()
    }

//    private suspend fun useOpenAi(scop: CoroutineScope, scopquestion: String) {
//        val openAI = OpenAI("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")
//        val ada = openAI.model(modelId = ModelId("text-ada-001"))
//        val completionRequest = CompletionRequest(
//            model = ada.id,
//            prompt = scopquestion
//        )
//
//        val moderation = openAI.moderations(
//            request = ModerationRequest(
//                input = listOf("I want to kill them.")
//            )
//        )
//        //               Log.e(ETAG,"moderation=="+moderation.results[0].categoryScores.)
//        openAI.completions(completionRequest)
//            .onEach { Log.e(ETAG, "onEach=====" + it.choices[0].text) }
//            .onCompletion { println() }
//            .launchIn(scop)
//            .join()
//    }

    override fun getActTtitle(): String {
        return "mainAct"
    }

    //测试ComposeUI 刷新范围,结论：
// 当控件里有可监听刷新State(Livedata StateFlow等)， 刷新范围为 @Composable方法内的，会重新走一遍@Composable注解的方法
// 所以当例如button(){text()}中的text是在@Composable内，就不会让当前方法重新走，而如果只是text(text=state)中的text更新时，就会引发@Composable重新走
// 当重新走@Composable注解的方法时，刷新的控件只包含对应监听state的控件，并不会引发都刷新，
//Viewmodel：ViewModelProvider(this)[TestViewModel::class.java]获取的ViewModel和方法中使用委拖viewModel()方法，获取的是同一个ViewModel
    @Composable
    private fun testss(viewmodel: TestViewModel = viewModel()): State<ExampleUiState?> {
        Log.e("ethan", "testss")
        Log.e("ethan", viewmodel.toString())
        Log.e("ethan", mViewmodel.toString())
        val data = viewmodel.uiViewEvent.observeAsState()
        Button(onClick = {
            viewmodel.uiViewEvent.value = ExampleUiState()
        }) {
            Text(text = data.value?.testContent ?: "empty")
        }
//        Text(text = data.value?.testContent ?: "empty", modifier = Modifier.clickable {
//            viewmodel.uiViewEvent.value = ExampleUiState()
//        })
//        testeee(data = data)
        return data
    }



    @Composable
    private fun testeee(viewmodel: TestViewModel = viewModel(), data: State<ExampleUiState?>) {
        Log.e("ethan", "eeeee")
        Log.e("ethan", viewmodel.toString())
        Log.e("ethan", mViewmodel.toString())

        TextButton(onClick = {
            viewmodel.uiViewEvent.value = ExampleUiState()
        }) {
            Log.e("ethan", "ttttttt22222")
            Text(text = "testttttt")
        }
        TextButton(onClick = {
            Log.e("ethan", "ttttttt33333")
            viewmodel.uiViewEvent.value = ExampleUiState()
        }) {
            Log.e("ethan", "ttttttt44444")
            Text(text = data.value?.testContent ?: "empty")
        }
        Text(text = data.value?.testContent ?: "empty", modifier = Modifier.clickable {
            Log.e("ethan", "ttttttt")
            viewmodel.uiViewEvent.value = ExampleUiState()
        })
    }

    @Composable
    private fun ListUI() {
        val srcoll = rememberLazyListState()
        LazyColumn(modifier = Modifier.fillMaxSize(), srcoll) {
            itemsIndexed(listData, itemContent = { index, jumpEntity ->
                Button(
                    modifier = Modifier
                        .padding(top = 15.dp, start = 15.dp, end = 15.dp)
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .shadow(shape = RoundedCornerShape(20), elevation = 5.dp, clip = true),
                    onClick = {
                        jumpActivity(jumpEntity)
                    }
                ) {
                    Text(
                        text = jumpEntity.value,
                        fontSize = 20.sp,
//                        style = androidx.compose.ui.text.TextStyle(color = Color.White),
                        color = MaterialTheme.colors.onBackground,
                        modifier = Modifier.align(alignment = Alignment.CenterVertically)
                    )
                }
            })
        }
    }

    private fun jumpActivity(jumpEntity: JumpEntity) {
        when (jumpEntity) {
            JumpEntity.ANIMATION -> {
                startActivity(Intent(this, AnimationActivity::class.java))
            }
            JumpEntity.MOTIONLAYOUT -> {
                startActivity(Intent(this, MotionLayoutActivity::class.java))
            }
            JumpEntity.FOUNDATION -> {
                startActivity(Intent(this, FoundationActivity::class.java))
            }
            JumpEntity.COLLAPSING -> {
                startActivity(Intent(this, CollapsingActiivty::class.java))
            }
            JumpEntity.BOTTOM_BAR -> {
                startActivity(Intent(this, BottomBar_PagerActivity::class.java))
            }
            JumpEntity.NAVIGATION -> {
                startActivity(Intent(this, NavigationActivity::class.java))
            }
            JumpEntity.PREVIEW_VIEWMODE -> {
                startActivity(Intent(this, PreviewByViewmodelActivity::class.java))
            }
            JumpEntity.GAME->{
                startActivity(Intent(this,GameActivity::class.java))
            }
            JumpEntity.ANIMATION2->{
                startActivity(Intent(this,Animation2Activity::class.java))
            }


        }
    }


    override fun onDestroy() {
        Log.e("ethan", "onDestroy");
        super.onDestroy()
    }
}

