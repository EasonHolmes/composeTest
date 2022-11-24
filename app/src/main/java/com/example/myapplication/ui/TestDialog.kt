package com.example.myapplication.ui

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.flowWithLifecycle
import com.example.myapplication.R
import com.example.myapplication.ui.vm.*
import com.example.myapplication.ui.widget.ClickRow
import com.example.myapplication.ui.widget.GradientButton
import com.example.myapplication.ui.widget.NoDescriptionImage

/**
 * Created by Ethan Cui on 2022/11/8
 */
class TestDialog : AppCompatDialogFragment() {

    override fun onStart() {
        super.onStart()
        val dm = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(dm)
        dialog!!.window!!.setLayout(dm.widthPixels, dialog!!.window!!.attributes.height)
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
//        dialog!!.window!!.setDimAmount(0.9f)////设置背景透明度，0~1.0  默认0.5
        dialog?.setCancelable(false)
        dialog?.setCanceledOnTouchOutside(false);

    }

    val annotatedString = buildAnnotatedString {
        withStyle(style = SpanStyle(color = Color.White)) {
            append("每领取一个红包")
        }
        withStyle(style = SpanStyle(color = Color.Red)) {
            append("都会有一笔红包存入存钱罐\n")
        }
        withStyle(style = SpanStyle(color = Color.White)) {
            append("记得明天来领哦")
        }
    }

    private val verticalGradientBrush = Brush.horizontalGradient(
        colors = listOf(
            Color(0xFFB9A29C),
            Color(0xff8D6E63),
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return ComposeView(requireContext()).also {
            it.setContent {
                UI()
            }
        }
    }

    @Composable
    fun UI(viewModel: TestViewModel2 = androidx.lifecycle.viewmodel.compose.viewModel()) {
        var content by remember {
            mutableStateOf(ExampleUiData())
        }
        viewModel.uiViewEvent.observeEvent(this){
            when(it){
                is ExampleUiState2.StateData->{
//                    content = it.response.value
                }
            }
        }

        Log.e("ethan","UI===")
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
        ) {
            IconButton(
                modifier = Modifier.align(alignment = Alignment.End),
                onClick = { dialog?.dismiss() }) {
                Image(
                    painter = painterResource(id = R.mipmap.dialog_close_icon),
                    contentDescription = ""
                )
            }
            Spacer(
                Modifier
                    .height(20.dp)
                    .fillMaxWidth()
            )
            NoDescriptionImage(
                imageResrouce = R.mipmap.dialog_close_icon,
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(20.dp))
            ClickRow(
                onclick = {
                    Toast.makeText(this@TestDialog.context, "ClickRow被点击了", Toast.LENGTH_SHORT)
                        .show()
//                    Log.e("ethan", "rourourouroruou=====" + it.x + "-=====" + it.y)
                },
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
                    //裁剪
                    .clip(RoundedCornerShape(25.dp))
                    // 边框 圆角（需要配合clip 注释clip可以看看）
                    .border(
                        3.dp,
                        colorResource(id = R.color.black),
                        shape = RoundedCornerShape(25.dp)
                    )
                    .background(
                        brush = Brush.horizontalGradient(//渐变背景
                            colors = listOf(
                                colorResource(id = R.color.white),
                                colorResource(id = R.color.red)
                            )
                        ),
//                        color = colorResource(id = R.color.red)
                    )
                    .padding(horizontal = 30.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                NoDescriptionImage(imageResrouce = R.mipmap.red_packet_icon)
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = "test",
                    style = TextStyle(fontSize = 15.sp, color = colorResource(id = R.color.white)),
                )
            }
            Text(
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
                    .padding(top = 15.dp), text = annotatedString,
                textAlign = TextAlign.Center
            )
            //！！！！！！！！！！！！！！！
            GradientButton(
                onClick = {
                    viewModel.changeData2()
                },
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
//                text = viewModel.stateData.value.content,
//                text = viewModel.stateData.value.content,
                gradient = verticalGradientBrush
            ){
                Text(text = viewModel.stateData.value.content)
            }
            Button(modifier = Modifier
                .fillMaxWidth()
                .align(alignment = Alignment.CenterHorizontally)
                .padding(top = 20.dp, start = 50.dp, end = 50.dp)
//                .clip(RoundedCornerShape(20.dp))//无效 因为button是在一个row里的，这里的clip backgroud设置都是外层
                ,
                colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.purple_200)),
                border = BorderStroke(3.dp, color = Color.Blue),
                shape = RoundedCornerShape(20.dp),//有效
                onClick = {
                    Toast.makeText(context, "button这是个提示", Toast.LENGTH_SHORT).show()
                }) {
                Text(modifier = Modifier
                    .padding(vertical = 5.dp)
                    .pointerInput(Unit) {
                        detectTapGestures(onTap = {
                            Toast
                                .makeText(context, "text被点击了", Toast.LENGTH_SHORT)
                                .show()
                        })
                    },
                    style = TextStyle(color = Color.White),
                    text = buildAnnotatedString {
                        withStyle(style = ParagraphStyle(lineHeight = 30.sp)) {
                            withStyle(style = SpanStyle(color = Color.Blue)) {
                                append("我是一个可点击文字")
                            }
                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Red
                                )
                            ) {
                                append("World")
                            }
                            append("Compose")
                        }
                    })
            }
        }
    }

    @Composable
    fun TestUi(viewModel: TestViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
        ) {
            val (button) = createRefs()
            Button(onClick = {

            }, Modifier.constrainAs(button) {
                top.linkTo(parent.top, 0.dp, 0.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            }) {
                Text(modifier = Modifier.pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        Log.e("ethan", "onTap")
                    }, onPress = {
                        Log.e("ethan", "onPress")
                    })
                }, text = AnnotatedString(text = "lakjsdlfkjalksjdf"))
            }
        }
    }

    @Preview()
    @Composable
    fun pr() {
        MaterialTheme {
            UI()
        }
    }
}