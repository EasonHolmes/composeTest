package com.example.myapplication.ui

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateSizeAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.myapplication.Logutils
import com.example.myapplication.R
import com.example.myapplication.ui.widget.ChangeImageSwitch
import com.example.myapplication.ui.widget.ChangeFontSwitch
import com.example.myapplication.ui.widget.ChangeNormalSwitch
import com.example.myapplication.ui.widget.ChangeStatusSwitchDefault
import com.example.myapplication.ui.widget.CircleProgress
import com.example.myapplication.ui.widget.GradientProgress
import com.example.myapplication.ui.widget.SwitchMaterial3Defaults
import com.example.myapplication.ui.widget.SwitchMaterial3
import com.example.myapplication.ui.widget.wave.DrawType
import com.example.myapplication.ui.widget.wave.WaveLoading
import com.example.myapplication.ui.widget.wave.rememberWaveDrawColor


/**
 * Created by Ethan Cui on 2023/5/12
 */
class Animation2Activity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    @Composable
    override fun ContentView() {
        val progressAnima = remember {
            Animatable(0f, Float.VectorConverter)
        }
        Column(
            Modifier
                .fillMaxSize()
                .background(Color.Gray)
                .verticalScroll(rememberScrollState())
        ) {
            ScrollText()
            FounctionMaterial3()
            CustomSwitch()
            Spacer(modifier = Modifier.height(15.dp))
            GradientProgress(
                progress = progressAnima.value,
                horizontalGradient = Brush.horizontalGradient(listOf(Color.Red, Color.Green)),
                backgroundColor = Color.White,
                progressPadding = PaddingValues(1.5.dp),
                roundedCornerShape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth()
                    .height(16.dp), anim = false
            )
            Material3ButtonExample()
            CustomCircleProgress()
            WaveUI()

        }
        LaunchedEffect(key1 = Unit, block = {
            progressAnima.animateTo(1f, tween(1000), block = {
            })
        })
    }

    @Composable
    private fun CustomSwitch() {
        ChangeImageSwitch(
            beginLeft = true,
            startImageVector = Icons.Default.Check,
            endImageVector = Icons.Default.Clear,
            leftOrRight = {
//                Log.e("ethan", "left111===$it")
            }, width = 85.dp, height = 30.dp
        )
        ChangeFontSwitch(
            beginLeft = true,
            startContent = "℃",
            endContent = "℉",
            leftOrRight = {
//                Log.e("ethan", "left222===$it")
            },
            colors = ChangeStatusSwitchDefault.colors(startThumbColor = Color.White),
            width = 85.dp,
            height = 30.dp
        )
        Spacer(modifier = Modifier.height(15.dp))
        ChangeNormalSwitch(
            beginLeft = true,
            leftOrRight = {
//                Log.e("ethan", "left222===$it")
            },
            colors = ChangeStatusSwitchDefault.colors(startThumbColor = Color.White),
            width = 100.dp,
            height = 30.dp,
        )
    }

    @Composable
    private fun Material3ButtonExample() {
        Row {
            androidx.compose.material3.Surface(
                shadowElevation = 10.dp,//z轴
                color = Color.Transparent,
                contentColor = Color.Transparent,
                shape = ButtonDefaults.shape
            ) {
                androidx.compose.material3.Button(
                    onClick = { },
                    elevation = ButtonDefaults.buttonElevation(0.dp, 5.dp, 5.dp, 5.dp, 0.dp)
                ) {
                    Text(text = "material3Button")
                }
            }
            androidx.compose.material3.Button(
                onClick = { },
                elevation = ButtonDefaults.buttonElevation(0.dp, 5.dp, 5.dp, 5.dp, 0.dp)//默认都是0
            ) {
                Text(text = "material3Button")
            }
        }
    }

    @Composable
    private fun CustomCircleProgress() {
        Row(verticalAlignment = Alignment.CenterVertically) {
            var progress by remember {
                mutableFloatStateOf(0f)
            }
            Box() {
                CircleProgress(
                    diameter = 100.dp,
                    borderWidth = 2.dp,
                    borderColor = Color.Red,
                    centerColor = Color.Black,
                    progress = {progress }
                )
                Text(
                    text = "${(progress * 100).toInt()}%",
                    color = Color.White,
                    modifier = Modifier.align(
                        Alignment.Center
                    )
                )
            }
            Slider(value = progress, valueRange = 0f..1f, onValueChange = {
                progress = it
            })
        }
    }

    @Composable
    private fun WaveUI() {
        Row {
            //纯水波纹可以这么设置
            Box(
                Modifier
                    .clip(RoundedCornerShape(360.dp))
                    .border(2.dp, Color.Green, RoundedCornerShape(360.dp))
            ) {
                WaveLoading(
                    progress = 0.5f, // 0f ~ 1f
                    backDrawType = rememberWaveDrawColor(color = Color.White),
                    foreDrawType = DrawType.DrawColor(Color.Red),
                    modifier = Modifier.size(100.dp)
                ) {
                    //                    Image(
                    //                        painter = painterResource(id = R.mipmap.jinzhu_icon),
                    //                        contentDescription = ""
                    //                    )
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .background(Color.Red)
                    )
                }
            }
            //index	backDrawType	foreDrawType	说明
            //1	DrawImage	DrawImage	背景灰度，前景原图
            //2	DrawColor(Color.LightGray)	DrawImage	背景单色，前景原图
            //3	DrawColor(Color.LightGray)	DrawColor(Color.Cyan)	背景单色，前景单色
            //4	None	DrawColor(Color.Cyan)	无背景，前景单色
            //振幅在WaveAnim里的maxWave调整
            Box(
                Modifier
            ) {
                WaveLoading(
                    progress = 0.5f, // 0f ~ 1f
                    backDrawType = DrawType.DrawColor(Color.White),//未到的颜色 DrawType.None就是没有颜色
                    //                    foreDrawType = DrawType.DrawColor(Color.Red),//波纹色
                    modifier = Modifier.size(100.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.mipmap.jinzhu_icon),
                        contentDescription = ""
                    )
                }
            }
        }
    }

    @Composable
    private fun ScrollText() {
        var target by remember {
            mutableIntStateOf(resources.displayMetrics.widthPixels + 120)
        }
        val anima by animateSizeAsState(
            targetValue = Size(target.toFloat(), 0f),
            animationSpec = infiniteRepeatable(
                tween(durationMillis = 5000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            ),
            label = "",
        )
        Box(
            modifier = Modifier
                .onGloballyPositioned {
                    target = -it.size.width
                }
                .offset(x = LocalDensity.current.run { (anima.width).toDp() }),
        ) {
            Text(
                text = "asdfa", modifier = Modifier,
//                    .offset(x = LocalDensity.current.run { (anima.width).toDp()}),
                overflow = TextOverflow.Clip,
                softWrap = false,
                onTextLayout = {
//                    target =  -it.size.width
                }
            )
        }
    }

    @Composable
    private fun FounctionMaterial3() {
        var checked by remember {
            mutableStateOf(false)
        }
        SwitchMaterial3(
            checked = checked,
            onCheckedChange = { checked = it },
            thumbContent = {
                Icon(imageVector = Icons.Default.Check, contentDescription = "")
            },
            colors = SwitchMaterial3Defaults.colors(
                uncheckedBorderColor = Color.Green,
                checkedBorderColor = Color.Red,
                checkedTrackColor = Color.Cyan,
                uncheckedTrackColor = Color.Blue,
                uncheckedThumbColor = Color.White,
//                checkedThumbColor = Color.White,
            )
        )
    }

}