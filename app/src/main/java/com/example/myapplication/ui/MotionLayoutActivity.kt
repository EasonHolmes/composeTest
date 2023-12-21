package com.example.myapplication.ui

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.*
import androidx.constraintlayout.compose.layoutId
import com.example.myapplication.R
import com.example.myapplication.ui.widget.ClickColumn
import com.example.myapplication.ui.widget.CustomProgressBar
import com.example.myapplication.ui.widget.CustomSeekbar

/**
 * Created by Ethan Cui on 2022/11/22
 */
//transitions属性 https://github.com/androidx/constraintlayout/wiki/Compose-MotionLayout-JSON-Syntax#transitions
class MotionLayoutActivity : BaseActivity() {

    @Composable
    override fun ContentView() {
        Box(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Column() {
            MotionLayoutButton()
            Spacer(modifier = Modifier.height(40.dp))
            MotionLayoutTest1()
            }
            MotionlayoutTest2()

        }
    }


    // on below line we are creating
// a motion layout button method.
    @OptIn(ExperimentalUnitApi::class, ExperimentalMotionApi::class)
    @Composable
    private fun MotionLayoutButton() {
        var animateButton by remember { mutableStateOf(false) }
        val buttonAnimationProgress by animateFloatAsState(
            targetValue = if (animateButton) 1f else 0f,
            animationSpec = tween(1000)
        )
        MotionLayout(
            ConstraintSet(
                """ {
                // on below line we are specifying width,height and margin
                // from start, top and end for button1
                button1: {
                  width: "spread",
                  height: 50,
                  start: ['parent', 'start', 16],
                  end: ['parent', 'end', 16],
                  top: ['parent', 'top', 0]
                },
                // on below line we are specifying width,height
                // and margin from start, top and end for button2
                button2: {
                  width: "spread",
                  height: 50,
                  start: ['parent', 'start', 16],
                  end: ['parent', 'end', 16],
                  top: ['button1', 'bottom', 16]
                }
            } """
            ),

            ConstraintSet(
                """ {
                // on below line we are specifying width,height and margin
                // from start, top and end for button1
                button1: {
                  width: 150,
                  height: 80,
                  start: ['parent', 'start', 30],
                  end: ['button2', 'start', 10]
                },
                // on below line we are specifying width,height
                // and margin from start, top and end for button2
                button2: {
                  width: 150,
                  height: 80,
                  start: ['button1', 'end', 10],
                  end: ['parent', 'end', 30]
                }
            } """
            ),
            progress = buttonAnimationProgress,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Button(
                onClick = {
                    animateButton = !animateButton
                },
                modifier = Modifier.layoutId("button1")
            ) {
                Column(
                    modifier = Modifier
                        .padding(3.dp)
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.mipmap.red_packet_icon),
                        contentDescription = "Python",
                        modifier = Modifier
                            .height(30.dp)
                            .width(30.dp)
                    )
                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        text = "Python",
                        color = Color.White,
                        fontSize = TextUnit(value = 18F, type = TextUnitType.Sp)
                    )
                }
            }
            Button(
                onClick = {
                    animateButton = !animateButton
                },
                modifier = Modifier.layoutId("button2")
            ) {
                Column(
                    modifier = Modifier
                        .padding(3.dp)
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    // on below line we are specifying vertical
                    // and horizontal arrangement for our column
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.mipmap.dialog_close_icon),

                        contentDescription = "Javascript",

                        modifier = Modifier
                            .height(30.dp)
                            .width(30.dp)
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = "JavaScript",
                        color = Color.White,
                        fontSize = TextUnit(value = 18F, type = TextUnitType.Sp)
                    )

                }
            }
        }
    }

    @OptIn(ExperimentalMotionApi::class)
    @Composable
    private fun MotionLayoutTest1() {
        var progressValue by remember {
            mutableStateOf(0f)
        }
        val buttonAnimationProgress by animateFloatAsState(
            targetValue = progressValue,
            animationSpec = tween(500)
        )
        MotionLayout(
//            start = ConstraintSet {
//                val button1 = createRefFor("button1")
//                val button2 = createRefFor("button2")
//                val progress = createRefFor("progress")
//                constrain(button1) {
//                    top.linkTo(parent.top)
//                    start.linkTo(parent.start)
//                    end.linkTo(parent.end)
//                }
//                constrain(button2) {
//                    top.linkTo(button1.bottom, margin = 15.dp)
//                    start.linkTo(parent.start)
//                    end.linkTo(parent.end)
//                }
//                constrain(progress) {
//                    top.linkTo(button2.bottom, margin = 15.dp)
//                    start.linkTo(parent.start)
//                    end.linkTo(parent.end)
//                }
//            },
            start = ConstraintSet(
                """ {
                // on below line we are specifying width,height and margin
                // from start, top and end for button1
                button1: {
                  width: "parent",
                  height: "wrap",
                  start: ['parent', 'start', 16],
                  end: ['parent', 'end', 16],
                  top: ['parent', 'top', 0]
                },
                // on below line we are specifying width,height
                // and margin from start, top and end for button2
                button2: {
                  width: "parent",
                  height: "wrap",
                  start: ['parent', 'start', 16],
                  end: ['parent', 'end', 16],
                  top: ['button1', 'bottom', 16]
                },
                  progress: {
                  width: "parent",
                  height: "wrap",
                  start: ['parent', 'start', 16],
                  end: ['parent', 'end', 16],
                  top: ['button2', 'bottom', 16]
                },
            } """
            ),

            ConstraintSet(
                """ {
                // on below line we are specifying width,height and margin
                // from start, top and end for button1
                button1: {
                  width: 150,
                  height: "wrap",
                  start: ['parent', 'start', 30],
                  end: ['button2', 'start', 10]
                },
                // on below line we are specifying width,height
                // and margin from start, top and end for button2
                button2: {
                  width: 150,
                  height:  "wrap",
                  start: ['button1', 'end', 10],
                  end: ['parent', 'end', 30]
                },
                  progress: {
                  width: "parent",
                  height: "wrap",
                  start: ['parent', 'start', 16],
                  end: ['parent', 'end', 16],
                  top: ['button2', 'bottom', 16]
                },
            } """
            ),
            progress = buttonAnimationProgress,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
        ) {
            Button(
                onClick = { },
                modifier = Modifier
                    .layoutId("button1")
            ) {
                Text(text = "滑动滚条1")
            }
            Button(
                onClick = { },
                modifier = Modifier
                    .layoutId("button2")
            ) {
                Text(text = "滑动滚条2")
            }
            CustomSeekbar(modifier = Modifier.layoutId("progress"), onProgressChanged = {
                progressValue = it
            },indicatorColor = Color.Red,backgroundIndicatorColor = Color.Green)
            //使用
            var progress by remember { mutableStateOf(50f) }
            CustomProgressBar(
                Modifier,
                progress = progress,
                onProgressChanged = { newProgress ->
                    progress = newProgress
                },
                progressColor = Color.Red
            )
        }

    }

    @OptIn(ExperimentalMotionApi::class)
    @Composable
    private fun MotionlayoutTest2() {
        var animationAction by remember { mutableStateOf(false) }
        val buttonAnimationProgress by animateFloatAsState(
            targetValue = if (animationAction) 1f else 0f,
            animationSpec = tween(1000)
        )
        MotionLayout(
            start = ConstraintSet {
                val item1 = createRefFor("item1")
                val item2 = createRefFor("item2")
                constrain(item1) {
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end, margin = 20.dp)
                }
                constrain(item2) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start, margin = 20.dp)
                }
            },
            end = ConstraintSet {
                val item1 = createRefFor("item1")
                val item2 = createRefFor("item2")
                constrain(item1) {
                    top.linkTo(parent.top)
                    start.linkTo(item2.end)
                }
                constrain(item2) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start, margin = 20.dp)
                }
            },
            progress = buttonAnimationProgress,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),

            transition = androidx.constraintlayout.compose.Transition(
                """
                         {
              from: 'start',
              to: 'end',
              pathMotionArc: 'startHorizontal',
              KeyFrames: {
                KeyAttributes: [
                  {
                    target: ['item1'],
                    frames: [0, 50, 100],
                    translationX: [0, -250, 0],
                    scaleX: [1, 0, 1],
                    scaleY: [1, 0, 1],
                  },
                   {
                    target: ['item2'],
                    frames: [0, 50, 100],
                    scaleX: [1, 0, 1],
                    scaleY: [1, 0, 1],
                  }
                ]
              }
              }
            """.trimIndent()
            )
        ) {

            ClickColumn(
                onClick = { animationAction = !animationAction },
                modifier = Modifier.layoutId("item1")
            ) {
                Text(text = "点我移动")
                Image(
                    painter = painterResource(id = R.mipmap.red_packet_icon),
                    contentDescription = ""
                )
            }
            Text(text = "我只是个锚⚓️", modifier = Modifier.layoutId("item2"))
        }
    }

    override fun getActTtitle(): String {
        return "MotionLayout"
    }
}