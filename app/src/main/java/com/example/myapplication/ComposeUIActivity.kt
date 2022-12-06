package com.example.myapplication

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.ui.*
import com.example.myapplication.ui.mytheme.CustomApplicationTheme
import com.example.myapplication.ui.vm.ExampleUiState
import com.example.myapplication.ui.vm.TestViewModel
import com.example.myapplication.ui.widget.ClickColumn
import com.example.myapplication.ui.widget.drawContent
import java.io.File
import java.time.format.TextStyle


/**
 * Created by Ethan Cui on 2022/3/11
 */
enum class JumpEntity(val value: String) {
    ANIMATION("animation"),
    FOUNDATION("foundation"),
    MOTIONLAYOUT("motionLayout"),
    COLLAPSING("Collapsing视差")
}

class ComposeUIActivity : BaseActivity() {
    val listData = arrayListOf<JumpEntity>(
        JumpEntity.ANIMATION,
        JumpEntity.FOUNDATION,
        JumpEntity.MOTIONLAYOUT,
        JumpEntity.COLLAPSING
    )

    private val mViewmodel by lazy {
        Log.e("ethan", "iiiiii")
        ViewModelProvider(this)[TestViewModel::class.java]
    }

    @Composable
    override fun ContentView() {
        Column {

            val ts = testss()
            testeee(data = ts)
            ListUI()
        }
    }

    override fun getActTtitle(): String {
        return "mainAct"
    }

    //测试ComposeUI 刷新范围,结论：
    // 当控件里有可临听刷新State(Livedata StateFlow等)， 刷新范围为 @Composable方法内的，会重新走一遍@Composable注解的方法
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
        }){
            Log.e("ethan","ttttttt22222")
            Text(text = "testttttt")
        }
        TextButton(onClick = {
            Log.e("ethan","ttttttt33333")
            viewmodel.uiViewEvent.value = ExampleUiState()
        }){
            Log.e("ethan","ttttttt44444")
            Text(text = data.value?.testContent ?: "empty")
        }
        Text(text = data.value?.testContent ?: "empty", modifier = Modifier.clickable {
            Log.e("ethan","ttttttt")
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
                        style = androidx.compose.ui.text.TextStyle(color = Color.White),
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
        }
    }


    override fun onDestroy() {
        Log.e("ethan", "onDestroy");
        super.onDestroy()
    }
}

