package com.example.myapplication

import android.annotation.SuppressLint
import android.app.usage.UsageStatsManager
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.os.Bundle
import android.provider.CalendarContract
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.ui.Animation2Activity
import com.example.myapplication.ui.AnimationActivity
import com.example.myapplication.ui.BaseActivity
import com.example.myapplication.ui.BottomBar_PagerActivity
import com.example.myapplication.ui.CollapsingActiivty
import com.example.myapplication.ui.FoundationActivity
import com.example.myapplication.ui.GamesActivity
import com.example.myapplication.ui.MotionLayoutActivity
import com.example.myapplication.ui.PreviewByViewmodelActivity
import com.example.myapplication.ui.ShareTransitionActivity
import com.example.myapplication.ui.singleActMutilScreen.SingleActivityMutilScreen
import com.example.myapplication.ui.ZhuanPanActivity
import com.example.myapplication.ui.coordinator.CoordinatorLayoutNatigationAct
import com.example.myapplication.ui.mytheme.ChangeColorApplicationTheme
import com.example.myapplication.ui.mytheme.ColorTheme
import com.example.myapplication.ui.mytheme.LightDarkTheme
import com.example.myapplication.ui.utils.TimerLifecycle
import com.example.myapplication.ui.vm.ExampleUiState
import com.example.myapplication.ui.vm.TestViewModel
import com.google.accompanist.insets.ExperimentalAnimatedInsets
import com.google.accompanist.insets.rememberImeNestedScrollConnection


/**
 * Created by Ethan Cui on 2022/3/11
 */
enum class JumpEntity(val value: String) {
    ANIMATION("animation"),
    FOUNDATION("viewmodel使用及重组范围"),
    MOTIONLAYOUT("motionLayout"),
    COLLAPSING("Collapsing视差"),
    BOTTOM_BAR("Bottombar"),
    PREVIEW_VIEWMODE("Viewmodel在Preview中使用"),
    ANIMATION2("animation2"),
    COORDINATORLAYOUT("CoordinatorLayoutCompose"),
    ZHUANPAN("转盘"),
    GAMES("游戏类"),
    SHARETRANSITION("compose共享元素动画"),
    SINGLEACTIVITYMUTILSCREEN("单activity多compose")
}

data class Users(var name: MutableState<String> = mutableStateOf(""))

class ComposeUIActivity : BaseActivity() {
    val listData = arrayListOf<JumpEntity>(
        JumpEntity.ANIMATION,
        JumpEntity.FOUNDATION,
        JumpEntity.MOTIONLAYOUT,
        JumpEntity.COLLAPSING,
        JumpEntity.BOTTOM_BAR,
        JumpEntity.PREVIEW_VIEWMODE,
        JumpEntity.ANIMATION2,
        JumpEntity.COORDINATORLAYOUT,
        JumpEntity.ZHUANPAN,
        JumpEntity.GAMES,
        JumpEntity.SHARETRANSITION,
        JumpEntity.SINGLEACTIVITYMUTILSCREEN,
    ).apply {
        reverse()
    }

    companion object {
        val ETAG = "ethan"
    }


    private val mViewmodel by lazy {
        ViewModelProvider(this)[TestViewModel::class.java]
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        Log.e("ethan", lifecycle.currentState.name)

//        lifecycleScope.launch {
//            val result2 = flowOf(1,2,3,4)
//                .scan(0) {last,next->
//                    last+next
//                }.map {  }.flowOn(Dispatchers.IO).onCompletion {  }
//                    .collect{
//
//                }
//            okhttpFlowRequest("", KeyModel.create())
//        }
//      var aaaa =   getAndroidId(this)
//        Logutils.e("aaaa===="+aaaa)

//        val agree =  packageManager.checkPermission(
//            Manifest.permission.WRITE_CALENDAR,
//            packageName
//        ) == PackageManager.PERMISSION_GRANTED;
//        if(agree){
//            addCalendarEventWithReminder(
//                title = "title",
//                description = "descrip",
//                startTime = 1720074620000,
//                endTime = 1720074620000,
//                reminderMinutes = 5
//            )
//        }else{
//            requestPermissions(arrayOf(Manifest.permission.WRITE_CALENDAR),1)
//        }
    }


    // 添加日历事件和提醒
    fun addCalendarEventWithReminder(
        title: String?,
        description: String?,
        startTime: Long,
        endTime: Long,
        reminderMinutes: Int
    ) {
        val cr = contentResolver
        val values = ContentValues()

        // 设置日历事件的基本信息
        values.put(CalendarContract.Events.CALENDAR_ID, 1) // 日历ID，可以根据实际情况修改
        values.put(CalendarContract.Events.TITLE, title) // 标题
        values.put(CalendarContract.Events.DESCRIPTION, description) // 描述
//        values.put(CalendarContract.Events.EVENT_LOCATION, "地点") // 地点
        values.put(CalendarContract.Events.DTSTART, startTime) // 开始时间
        values.put(CalendarContract.Events.DTEND, endTime) // 结束时间
        values.put(CalendarContract.Events.EVENT_TIMEZONE, "CST") // 时区

        // 添加日历事件
        val uri = cr.insert(CalendarContract.Events.CONTENT_URI, values)
        val eventID = uri!!.lastPathSegment!!.toLong()

        // 添加提醒
        val reminderValues = ContentValues()
        reminderValues.put(CalendarContract.Reminders.EVENT_ID, eventID)
        reminderValues.put(
            CalendarContract.Reminders.METHOD,
            CalendarContract.Reminders.METHOD_ALERT
        )
        reminderValues.put(CalendarContract.Reminders.MINUTES, reminderMinutes)
        cr.insert(CalendarContract.Reminders.CONTENT_URI, reminderValues)
    }


    private fun getUsageStats(context: Context) {
        val usageStatsManager = context.getSystemService(USAGE_STATS_SERVICE) as UsageStatsManager

        val currentTime = System.currentTimeMillis()
        val startTime = currentTime - (24 * 60 * 60 * 1000) // 从当前时间的前一天开始获取数据

        val stats = usageStatsManager.queryUsageStats(
            UsageStatsManager.INTERVAL_BEST, currentTime - 2000, currentTime
//            startTime,
//            currentTime
        )

        for (usageStats in stats) {
            val packageName = usageStats.packageName
            val totalTimeInForeground = usageStats.totalTimeInForeground

            Logutils.e("eeeee==" + packageName)
            Logutils.e("eeeee11111==" + totalTimeInForeground)
        }
        if (stats.isEmpty()) {
            try {
                startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
            } catch (e: Exception) {
                Toast.makeText(this, "无法开启允许查看使用情况的应用界面", Toast.LENGTH_LONG)
                    .show();
                e.printStackTrace();
            }
        }
    }


    @SuppressLint("HardwareIds")
    fun getAndroidId(context: Context): String {
        return Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
    }

    override fun onRestart() {
        super.onRestart()
        Log.e("ethan", lifecycle.currentState.name)
    }

    override fun onResume() {
        super.onResume()
        Log.e("ethan", lifecycle.currentState.name)
//        getUsageStats(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("ethan", lifecycle.currentState.name)
    }

    @OptIn(ExperimentalAnimatedInsets::class)
    @Composable
    override fun ContentView() {
        var changeColor by remember {
            mutableStateOf(ColorTheme.WHITE)
        }
        var content by remember {
            mutableStateOf("上拉有键盘出来动画")
        }
//键盘动画是另的这句，通过这个库com.google.accompanist:accompanist-insets
        //同时需要ProvideWindowInsets(windowInsetsAnimationsEnabled: Boolean = true,){}来包裹要不会有奇怪的问题
        Column(Modifier.nestedScroll(rememberImeNestedScrollConnection())) {
            //还有lightDarkTheme根据主题变化
            ChangeColorApplicationTheme(changeColor) {
                Button(onClick = {
                    changeColor = ColorTheme.BLACK
                }) {
                    Text(text = "改变主题")
                }
                LightDarkTheme() {
                    Button(onClick = {
                    }, colors = ButtonDefaults.buttonColors(MaterialTheme.colors.primary)) {
                        Text(text = "使用matertheme改变主题，切换系统LightTheme DarkTheme看效果")
                    }
                }
                Column(Modifier.weight(1f)) {
                    ListUI()

                }
            }
            TextField(value = content, onValueChange = {
                content = it
            })

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
        return ""
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
                    },
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colors.primary)
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


            JumpEntity.PREVIEW_VIEWMODE -> {
                startActivity(Intent(this, PreviewByViewmodelActivity::class.java))
            }

            JumpEntity.GAMES -> {
                startActivity(Intent(this, GamesActivity::class.java))
            }

            JumpEntity.ANIMATION2 -> {
                startActivity(Intent(this, Animation2Activity::class.java))
            }

            JumpEntity.COORDINATORLAYOUT -> {
                startActivity(Intent(this, CoordinatorLayoutNatigationAct::class.java))
            }

            JumpEntity.ZHUANPAN -> {
                startActivity(Intent(this, ZhuanPanActivity::class.java))
            }

            JumpEntity.SHARETRANSITION -> {
                startActivity(Intent(this, ShareTransitionActivity::class.java))
            }

            JumpEntity.SINGLEACTIVITYMUTILSCREEN -> {
                startActivity(Intent(this, SingleActivityMutilScreen::class.java))
            }

        }
    }


}
//class TTT :WebViewClient(){
//    override fun shouldInterceptRequest(
//        view: WebView?,
//        request: WebResourceRequest?
//    ): WebResourceResponse? {
//        val headers = request!!.requestHeaders
//        headers["X-Requested-With"] = "com.peacocktv.peacockandroid"
//
//        return try {
//            val url = URL(request!!.url.toString())
//            val conn = url.openConnection() as HttpURLConnection
//
//            for (header in headers.keys) {
//                conn.setRequestProperty(header, headers[header])
//            }
//
//            val contentType = conn.contentType
//            val mime = contentType.split(";")[0]
//
//            val webResponse=WebResourceResponse(
//                mime,
//                conn.contentEncoding,
//                conn.inputStream
//            )
//
//            var rmap= mutableMapOf<String,String>()
//            val rheaders: Map<*, *> = conn.headerFields
//            val keys: Set<String> = rheaders.keys as Set<String>
//            for (key in keys) {
//                val `val` = conn.getHeaderField(key)
//                println("$key    $`val`")
//                rmap[key]=`val`
//            }
//
//            webResponse.responseHeaders=rmap
//
//            webResponse
//        } catch (e: Exception) {
//            e.printStackTrace()
//            null
//        }
//    }
//}

