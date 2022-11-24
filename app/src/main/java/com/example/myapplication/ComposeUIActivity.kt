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
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.myapplication.ui.*
import com.example.myapplication.ui.mytheme.CustomApplicationTheme
import com.example.myapplication.ui.widget.ClickColumn
import com.example.myapplication.ui.widget.drawContent
import java.io.File
import java.time.format.TextStyle


/**
 * Created by Ethan Cui on 2022/3/11
 */
enum class JumpEntity(val value:String){
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


    @Composable
    override fun ContentView() {
        ListUI()
    }

    override fun getActTtitle(): String {
        return "mainAct"
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
                        .shadow(shape = RoundedCornerShape(20), elevation = 5.dp, clip = true,),
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
    private fun jumpActivity(jumpEntity:JumpEntity){
        when(jumpEntity){
            JumpEntity.ANIMATION->{
                startActivity(Intent(this,AnimationActivity::class.java))
            }
            JumpEntity.MOTIONLAYOUT->{
                startActivity(Intent(this,MotionLayoutActivity::class.java))
            }
            JumpEntity.FOUNDATION->{
                startActivity(Intent(this,FoundationActivity::class.java))
            }
            JumpEntity.COLLAPSING->{
                startActivity(Intent(this,CollapsingActiivty::class.java))
            }
        }
    }


    override fun onDestroy() {
        Log.e("ethan", "onDestroy");
        super.onDestroy()
    }
}

