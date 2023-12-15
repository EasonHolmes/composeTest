package com.example.myapplication.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.*
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.Logutils
import com.example.myapplication.R
import com.example.myapplication.ui.mytheme.LightDarkTheme
import com.example.myapplication.ui.vm.ExampleUiData
import com.example.myapplication.ui.vm.ExampleUiState
import com.example.myapplication.ui.vm.SingleLivedata
import com.example.myapplication.ui.vm.TestViewModel
import com.example.myapplication.ui.widget.GradientButton
import com.example.myapplication.ui.widget.GradientButton2
import com.example.myapplication.ui.widget.createCircularReveal
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * Created by Ethan Cui on 2022/10/28
 */
class FoundationActivity : BaseActivity() {
    private val mViewmodel: TestViewModel by lazy {
        ViewModelProvider(this)[TestViewModel::class.java]
    }
    val gradient =
        Brush.horizontalGradient(listOf(Color(0xFFC6E270), Color(0xFF00BEB2)))


    @Preview(showSystemUi = true)
    @Composable
    private fun PreviewUI() {
        TestButtons(MutableStateFlow(ExampleUiData("content")), SingleLivedata())
    }

    @Composable
    override fun ContentView() {
        Column {

            TestButtons(mViewmodel.uiEvent, mViewmodel.uiViewEvent)
            SnackDetail()
            Test()
        }
    }

    override fun getActTtitle(): String {
        return "Foundation"
    }

    //委托形式初始化viewmodel 同页面下的Composeable的viewmodel如果已经初始化会返回同一个
    @Composable
    fun TestButtons(
        uiEvent: MutableStateFlow<ExampleUiData>,
        uiViewEvent: SingleLivedata<ExampleUiState>
    ) {
        val stateData2 by uiEvent.collectAsStateWithLifecycle()
        Column(
            modifier = Modifier
                .fillMaxWidth(), verticalArrangement = Arrangement.Center
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                onClick = {
                    changeData()
                }
            ) {
                Text(text = "Viewmodel state使用==${stateData2.content}")
            }
            MaterialButton(uiViewEvent)

        }
    }


    //重组的范围：非inline且无返回值的Composable函数或lambda块
    @Composable
    fun SnackDetail() {
        Logutils.e("snack====")
        var ss by remember {
            mutableStateOf("000000")
        }

        Box {
            Text(text = ss)
//            Title(tt = ss)//假设title在这里时并且参数使用的是ss的直接引用，那么就会向上寻找到SnackDetail方法打印snack====
            //因为column是inline会向上去找非inline且无返回值的Composable函数或lambda块

            //假设title在这里时并且参数使用的是ss的直接引用，只会让Title方法的内部重组
            //因为ss是使用的lambda块而不是单纯的参数
//                Title {
//                    ss
//                }
            Button(onClick = {
                ss = "============================="
            }) {
                Logutils.e("button=====")
                //假设title在这里时并且参数使用的是ss的直接引用，那么在button的@compose范围内都会重组,
                // 但不会引发column有以上方法重组
                //因为button是非inline且无返回值的Composable函数
//                Title(tt = ss)
            }
        }
    }

    //这种写法tt的值是与ss关联所以ss在变化时就会引起snackDetail重组打印snack====和title====
    // 而Lambda的方式就相当于给Title传进的是Lambda而不直接是ss
    @Composable
    private fun Title(tt: String) {
        Logutils.e("title====")
        Text(text = tt)
    }

    //这种写法在是将ss变成了一个Lambda，所以他的更新重组就在Title方法中，而不是SnackDetail方法中,就只会打印Title====
    @Composable
    private fun Title(tt: () -> String) {
        Logutils.e("title====")
        Text(text = tt.invoke())
    }

    private fun changeData() {
        mViewmodel.changeData()
    }

    @Composable
    fun MaterialButton(uiViewEvent: SingleLivedata<ExampleUiState>) {
        Log.e("ethan", "TestButtons===22")
        val stateData = uiViewEvent.observeAsState()

        Button(onClick = {
            mViewmodel.changeData(33)
        }) {
            Text(
                text = stateData.value?.exampleUiData?.content
                    ?: "click me singleLiveData ViewModel changeUI"
            )
        }
    }
    //在这里点击button后会打印test1 2 7 4 6 8 9 10 5
    //因为block2中的text有引用state,而block2是非inline会向上打钱test1，
    //下面block10中的text有引用state,就会向上寻找到card(非inline@compose)，所以9 10会打印，
    //又因为block10是在block468中所以468并且也都是inline的所以也会重组打印
    @Composable
    fun Test() {//block1
        Logutils.e("Test: 1")
        var count by remember {
            mutableStateOf(0)
        }
        Column {//block2
            Logutils.e("Test: 2")
            Button(onClick = {//block-onclick
                Logutils.e("Test: onClick")
                count++
            }) {//block3
                Logutils.e("Test: 3")
                Text(text = "+")
            }

            Text(text = "$count")
            Box {//block7
                Logutils.e("Test: 7")
            }
        }
        Box {//block4
            Logutils.e("Test: 4")
            Column {//block6
                Logutils.e("Test: 6")
                Row {//block8
                    Logutils.e("Test: 8")
                    Card {//block9
                        Logutils.e("Test: 9")
                        Box {//block10
                            Logutils.e("Test: 10")
                            Text(text = "$count")
                        }
                    }
                }
            }
        }
        Logutils.e("Test: 5")
    }
}