package com.example.myapplication.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateSizeAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
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
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.Logutils
import com.example.myapplication.R
import com.example.myapplication.ui.widget.ChangeImageSwitch
import com.example.myapplication.ui.widget.ChangeFontSwitch
import com.example.myapplication.ui.widget.ChangeNormalSwitch
import com.example.myapplication.ui.widget.ChangeStatusSwitchDefault
import com.example.myapplication.ui.widget.CheckBoxGroup
import com.example.myapplication.ui.widget.CircleProgress
import com.example.myapplication.ui.widget.RowTabStyleDefault
import com.example.myapplication.ui.widget.GradientProgress
import com.example.myapplication.ui.widget.PinTuWidget
import com.example.myapplication.ui.widget.RowTabUI
import com.example.myapplication.ui.widget.SwitchMaterial3Defaults
import com.example.myapplication.ui.widget.SwitchMaterial3
import com.example.myapplication.ui.widget.TabStyle
import com.example.myapplication.ui.widget.UploadProgressButton
import com.example.myapplication.ui.widget.dialog.WithdrawCashDialog
import com.example.myapplication.ui.widget.wave.DrawType
import com.example.myapplication.ui.widget.wave.WaveLoading
import com.example.myapplication.ui.widget.wave.rememberWaveDrawColor
import com.skydoves.flexible.bottomsheet.material.FlexibleBottomSheet
import com.skydoves.flexible.core.FlexibleSheetSize
import com.skydoves.flexible.core.FlexibleSheetValue
import com.skydoves.flexible.core.rememberFlexibleBottomSheetState
import kotlinx.coroutines.launch
import kotlin.math.roundToInt


/**
 * Created by Ethan Cui on 2023/5/12
 */
class Animation2Activity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @Preview(showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
    @Composable
    private fun PP() {
        CustomSwitch()

    }

    private val bottomSheetShow = mutableStateOf(false)
    private val bottomDialog by lazy {
        WithdrawCashDialog().apply {
            setIshideable(true)
            this.setOnclickListener {
                this.dismiss()
            }
        }
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
            Material3Switch()
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
            Row {
                var progress by remember {
                    mutableFloatStateOf(.5f)
                }
                CustomCircleProgress(Modifier.weight(1f)) {
                    progress = it
                }
                WaveUI(progress)
            }
            CustomRowTabUI()
            UploadBtn()
            Button(onClick = {
                bottomDialog.show(supportFragmentManager, "")
            }) {
                Text(text = "bottomsheetDialog")
            }
            Button(onClick = {
                bottomSheetShow.value = true
            }) {
                Text(text = "FlexibleBottomSheet")
            }
            if (bottomSheetShow.value) {
                BottomSheet { bottomSheetShow.value = false }
            }
            Row {//使用row就是横向排列column就纵向排列
                CheckBoxGroup(
                    checkedKey = "nnn",
                    strs = mutableListOf("nnn", "mmm", "ssss"),
                    textStyle = TextStyle(),
                    checkedColor = Color.Blue,
                    onChanged = { key ->
                        Logutils.e("currentCheck==$key")
                    }
                )
            }
            DragView()
        }
        LaunchedEffect(key1 = Unit, block = {
            progressAnima.animateTo(1f, tween(1000), block = {
            })
        })
    }

    @Composable
    private fun UploadBtn() {
        var click by remember {
            mutableStateOf(false)
        }
        UploadProgressButton(
            width = 120.dp,
            height = 40.dp,
            realProgress = animateFloatAsState(
                targetValue = if (click) 1f else 0f,
                tween(2500), label = ""
            ).value,
            borderStroke = 5.dp,
            progressColor = Color.Red,
            buttonColor = MaterialTheme.colors.primary
        ) { click = !click }
    }

    @Composable
    private fun CustomRowTabUI() {
        var selectIndex by remember {
            mutableIntStateOf(0)
        }
        RowTabUI(
            tabPadding = PaddingValues(10.dp),
            items = listOf("Avatar", "Emoticons", "oqijwe"),
            rowTabStyle = RowTabStyleDefault.style(
                roundedCornerShape = RoundedCornerShape(360.dp),
                tabBgBursh = Brush.horizontalGradient(
                    listOf(
                        Color(0xFF8A67F7),
                        Color(0xFF6785FF)
                    )
                ),
                rowTabStyle = TabStyle.ROUND,
                selectTextColor = Color.Black,
                unSelectTextColor = Color.Gray,
                elevation = 5.dp,
//                bgColor = Color.White
            ),
            animationSpec = tween(350, easing = LinearEasing),
            onSelectIndex = {
////                                scop.launch {
////                                    pagerState.animateScrollToPage(it)
////                                }
//                selectIndex = it
                Log.e("ethan", "--=-=-==" + it)
            },
            animFinish = {
                selectIndex = it
                Log.e("ethan", "animFinish--=-=-==" + it)

            },
        ) { index, item ->
            Text(
                text = item,
                color = if (selectIndex == index) Color.Black else Color.Gray,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 16.sp
            )
        }
    }

    //https://github.com/skydoves/FlexibleBottomSheet?tab=readme-ov-file
    @Composable
    private fun BottomSheet(onDismissRequest: () -> Unit) {
        val scope = rememberCoroutineScope()
        //默认最小展开设置
        val sheetState = rememberFlexibleBottomSheetState(
            flexibleSheetSize = FlexibleSheetSize(),
            isModal = true,//false弹框下面的界面也可以交互，同时也就没有遮罩
            skipSlightlyExpanded = false,
            skipIntermediatelyExpanded = true,
        )

        FlexibleBottomSheet(
            sheetState = sheetState,
            containerColor = Color.Black,
            onDismissRequest = onDismissRequest,
        ) {
            Button(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = {
                    scope.launch {
                        if (sheetState.swipeableState.currentValue == FlexibleSheetValue.SlightlyExpanded) {
                            sheetState.fullyExpand()
//                            FlexibleSheetValue.IntermediatelyExpanded -> sheetState.fullyExpand()
                        } else {
                            sheetState.slightlyExpand()
                        }
                    }
                },
            ) {
                Text(text = "Expand type")
            }
            Button(onClick = {
                scope.launch {
                    sheetState.hide()
                }.invokeOnCompletion {
                    if (!sheetState.isVisible) {
                        onDismissRequest.invoke()
                    }
                }
            }) {
                Text(text = "close")
            }
        }
    }

    @Composable
    private fun CustomSwitch() {
        Row {

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
    private fun CustomCircleProgress(modifier: Modifier, progress: (Float) -> Unit) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier) {
            var progress by remember {
                mutableFloatStateOf(0f)
            }
            Box() {
                CircleProgress(
                    diameter = 80.dp,
                    borderWidth = 2.dp,
                    borderColor = Color.Red,
                    centerColor = Color.Black,
                    progress = { progress }
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
                progress(progress)
            }, modifier = Modifier.weight(1f))
        }
    }

    @Composable
    private fun WaveUI(progress: Float) {
        Row {
            //纯水波纹可以这么设置
            Box(
                Modifier
                    .clip(RoundedCornerShape(360.dp))
                    .border(2.dp, Color.Green, RoundedCornerShape(360.dp))
            ) {
                WaveLoading(
                    progress = progress, // 0f ~ 1f
                    backDrawType = rememberWaveDrawColor(color = Color.White),
                    foreDrawType = DrawType.DrawColor(Color.Red),
                    modifier = Modifier.size(80.dp)
                ) {
                    //                    Image(
                    //                        painter = painterResource(id = R.mipmap.jinzhu_icon),
                    //                        contentDescription = ""
                    //                    )
                    Box(
                        modifier = Modifier
                            .size(80.dp)
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
                    progress = progress, // 0f ~ 1f
                    backDrawType = DrawType.DrawColor(Color.White),//未到的颜色 DrawType.None就是没有颜色
                    //                    foreDrawType = DrawType.DrawColor(Color.Red),//波纹色
                    modifier = Modifier.size(80.dp)
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
    private fun Material3Switch() {
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


    @Composable
    fun DragView() {
        var moveOffset by remember {
            mutableStateOf(IntOffset(0, 0))
        }
        var lastX by remember {
            mutableIntStateOf(0)
        }
        var lastY by remember {
            mutableIntStateOf(0)
        }
        Box(modifier = Modifier
            .offset {
                moveOffset
            }
            .size(50.dp, 50.dp)
            .background(Color.Red)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDrag = { change, dragAmount ->
                        change.consume()
                        lastX += dragAmount.x.toInt()
                        lastY += dragAmount.y.toInt()
                        moveOffset = IntOffset(lastX, lastY)
                    },
                )

            }){
            Text(text = "拖拽方块",modifier = Modifier.align(alignment = Alignment.Center))
        }
    }

}