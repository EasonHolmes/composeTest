package com.example.myapplication.ui.widget

import android.content.Intent
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.Logutils
import com.example.myapplication.R
import com.example.myapplication.ui.FoundationActivity
import com.example.myapplication.ui.mytheme.LightDarkTheme
import com.example.myapplication.ui.theme.Purple200
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch


/**
 * Created by Ethan Cui on 2022/3/11
 */

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun drawContent(imgId: Int, context: AppCompatActivity) {
//    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Open))
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    // actual composable state
    var value by remember { mutableStateOf(0) }
    val list = arrayListOf(
        "1",
        "1",
        "1",
        "1",
    ).toMutableStateList()//LazyColumn等lazy列需要使用state类型的类才可以
    Scaffold(
        //单独设置shape
        drawerShape = RoundedCornerShape(0.dp),
        backgroundColor = Color.White,
        //抽屉组件中内容
        drawerContent = {

            Column(
                Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .size(275.dp, 130.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFF5FA777))
                    //滚动
                    .verticalScroll(rememberScrollState())
            ) {
                repeat(100) {
                    Text(
                        text = "抽屉组件中内容$it",
                        modifier = Modifier.padding(top = 10.dp),
                        Color.Gray
                    )
                }

            }
//            cells 将每列设置为至少 128.dp 宽
            LazyVerticalGrid(
                modifier = Modifier.height(200.dp),
                columns = GridCells.Adaptive(minSize = 123.dp)
            ) {
                item(span = {
                    // LazyGridItemSpanScope:
                    // maxLineSpan
                    GridItemSpan(2)
                }) {
                    Text(
                        text = "LazyVerticalGrid_Header....LazyVerticalGrid_Header",
                        modifier = Modifier.fillMaxWidth(),
                        fontSize = 20.sp
                    )
                }
                itemsIndexed(items = list) { index, item ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(id = R.mipmap.red_packet_icon),
                                contentDescription = "",
                                modifier = Modifier
                                    .width(40.dp)
                                    .height(40.dp)
                            )
                            Column {
                                Text("artist.name", color = Color.Black)
                                Text("artist.lastSeenOnline", color = Color.Black)
                            }
                        }
                        Text(
                            text = "抽屉组件中内容$item",
                            //在设置size之前设置padding相当于外边距
                            modifier = Modifier
                                .padding(top = 10.dp)
                                //此时组件占据空间大小100.dp+外边距 即大小为110.dp*110.dp
                                .size(100.dp)
                                //在设置size之后设置相当于内边距，组件大小不变
                                .padding(top = 10.dp)
                                //设置背景,对应背景来说，在它之前设置的padding 就相当于外边距，所以背景的绘制大小只有90.dp*90.dp
                                .background(Color.Blue)
                                .padding(top = 20.dp),//内边距，背景大小不变
                            Color.Gray
                        )
                    }
                }
            }
            val listState = rememberLazyListState()
            // Show the button if the first visible item is past
            // the first item. We use a remembered derived state to
            // minimize unnecessary compositions
            val showButton by remember(listState) {
                derivedStateOf {
                    Log.e("ethan", "showButtonIndex===" + listState.firstVisibleItemIndex)
                    listState.firstVisibleItemIndex > 0
                }
            }
            //loadmore
            LoadMoreListHandler(listState = listState) {
                Handler().postDelayed({ list.add("2222222") }, 1500)
            }
            //官方推荐使用的滚动
            LazyColumn(
                Modifier.fillMaxSize(),
                state = listState,//列表滚动监听
                contentPadding = PaddingValues(vertical = 55.dp, horizontal = 15.dp),//内边距
                verticalArrangement = Arrangement.spacedBy(50.dp),//行间距
            ) {
                item {
                    Text(text = "list_header")
                }
                itemsIndexed(
                    items = list
                    //// The key is important so the Lazy list can remember your
                    //          // scroll position when more items are fetched!
                    //key = { item -> item.hashCode() }) // //key = 您可以为每个列表项提供一个稳定的唯一键，为 key 参数提供一个块。提供稳定的键可使项状态在发生数据集更改后保持一致：不过，对于可用作项键的类型有一条限制。键的类型必须受 Bundle 支持，这是 Android 的机制，旨在当重新创建 activity 时保持相应状态。Bundle 支持基元、枚举或 Parcelable 等类型。
                ) { index, item ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .animateItemPlacement(
                                tween(durationMillis = 250)//也可以自定义动画
                            )//列表动画 类型recyclerview 添加删除item
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(id = R.mipmap.red_packet_icon),
                                contentDescription = "",
                                modifier = Modifier
                                    .width(40.dp)
                                    .height(40.dp)
                            )
                            Column {
                                Text("artist.name", color = Color.Black)
                                Text("artist.lastSeenOnline", color = Color.Black)
                            }
                        }
                        TextButton(
                            //在设置size之前设置padding相当于外边距
                            modifier = Modifier
                                .padding(top = 10.dp)
                                //此时组件占据空间大小100.dp+外边距 即大小为110.dp*110.dp
                                .size(100.dp)
                                //在设置size之后设置相当于内边距，组件大小不变
                                .padding(top = 10.dp)
                                //设置背景,对应背景来说，在它之前设置的padding 就相当于外边距，所以背景的绘制大小只有90.dp*90.dp
                                .background(Color.Blue)
                                .padding(top = 20.dp),//内边距，背景大小不变
                            onClick = {
//                                scope.launch {
//                                    list.add("22222")
//                                    listState.animateScrollToItem(list.size-1)//控制滚动位置
//                                }
                            },
                            content = {
                                Log.e("ethan", index.toString())
                                Text(text = "抽屉组件中内容$index$item", color = Gray)
                            }
                        )
                    }
                }
                item {
                    AnimatedVisibility(visible = showButton) {
                        Text(text = "list_footer")
                        CircularProgressIndicator()
                    }
                }
            }
        },
        drawerGesturesEnabled = true,//是否响应拖动：
        scaffoldState = scaffoldState,
        //标题栏区域
        topBar = {
            TopAppBar(
                backgroundColor = Color.White,
                elevation = 5.dp,
                title = {
                    TextButton(onClick = {
                        context?.startActivity(Intent(context, FoundationActivity::class.java))
                    }) {
                        Text(text = "titlsssss")
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            scope.launch {
                                scaffoldState.drawerState.apply {
                                    if (isClosed) open() else close()
                                }
                            }
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = imgId), contentDescription = ""
                        )
                    }
                }
            )
        },
        snackbarHost = {
            // reuse default SnackbarHost to have default animation and timing handling
            SnackbarHost(it) { data ->
                Snackbar(
                    modifier = Modifier.padding(8.dp),
                    content = {
                        Text(

                            text = data.message,
                            style = MaterialTheme.typography.body2,
                            color = Color.White
                        )
                    },
                    action = {
                        data.actionLabel?.let { actionLabel ->
                            TextButton(
                                onClick = {
                                    scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()

                                }
                            ) {
                                Text(
                                    text = actionLabel,
                                    style = MaterialTheme.typography.body2,
                                    color = Color.White
                                )
                            }
                        }
                    }
                )
            }
        },
        //悬浮按钮
        floatingActionButton = {
            ExtendedFloatingActionButton(
                backgroundColor = Purple200,
                text = { Text("悬浮按钮", color = Color.Black) },
                onClick = {
                    scope.launch {
                        value += 1
                        scaffoldState.snackbarHostState.showSnackbar("显示", actionLabel = "label")
                    }
                },
            )
        },
        floatingActionButtonPosition = FabPosition.End,
        //屏幕内容区域
        content = { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                content(value = value)
                Typewriter(delayMill = 120, text = "点击看打字机效果")
                UploadAnimation()
                AnimationAsStateAndAnimateTo(context)
                AnimatedContentTest()
                SizeChangeAnimatedContent()
                ExpandInAnimation()
                AnimateAsStateDemo()
                UpdateTransitionDemo()
                AnimateVisibilityDemo()
                AnimateContentSizeDemo()
                CrossfadeDemo()
            }
        },
    )
}

@Composable
fun AnimateContentSizeDemo() {
    var expend by remember { mutableStateOf(false) }

    Column(Modifier.padding(16.dp)) {
        Text("AnimateContentSizeDemo")
        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {
                expend = !expend
            }
        ) {
            Text(if (expend) "Shrink" else "Expand")
        }
        Spacer(Modifier.height(16.dp))

        Box(
            Modifier
                .background(Color.LightGray)
                .animateContentSize()//animateContentSize是Modifier的扩展方法，添加这个方法的Composable，会监听子Composable大小的变化，并以动画方式作成相应调整
        ) {
            Text(
                text = "animateContentSize() animates its own size when its child modifier (or the child composable if it is already at the tail of the chain) changes size. " +
                        "This allows the parent modifier to observe a smooth size change, resulting in an overall continuous visual change.",
                fontSize = 16.sp,
                textAlign = TextAlign.Justify,
                modifier = Modifier.padding(16.dp),
                maxLines = if (expend) Int.MAX_VALUE else 2
            )
        }
    }
}

@Composable
fun LoadMoreListHandler(listState: LazyListState, buffer: Int = 1, onLoaderMore: () -> Unit) {
    val loadMore = remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val totalItemsCount = layoutInfo.totalItemsCount
            val lastVisibleItemIndex = (layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0) + 1
            val b = lastVisibleItemIndex > (totalItemsCount - buffer)
//            Log.e(
//                "ethan",
//                "lastVisibleItemIndex:${lastVisibleItemIndex},totalItemsCount:${totalItemsCount},buffer:${buffer},result:${b}"
//            )
            b
        }
    }

    LaunchedEffect(loadMore)
    {
        snapshotFlow { loadMore.value }
            .distinctUntilChanged()
            .collect {
                if (it) {
                    Logutils.e("more")
                    onLoaderMore()
                }
            }
    }
}

//布局切换动画
@Composable
fun CrossfadeDemo() {

    var scene by remember { mutableStateOf(true) }

    Column(Modifier.padding(16.dp)) {

        Text("AnimateVisibilityDemo")
        Spacer(Modifier.height(16.dp))

        Button(onClick = {
            scene = !scene
        }) {
            Text("toggle")
        }

        Spacer(Modifier.height(16.dp))

        //布局切换动画
        Crossfade(
            targetState = scene,
//            animationSpec = tween(durationMillis = 1500)
            animationSpec = tween(
                durationMillis = 500,
                delayMillis = 0,
                easing = FastOutLinearInEasing
            )
        ) {
            if (it) {
                Text(text = "Phone", fontSize = 32.sp)
            } else {
                Icon(
                    imageVector = Icons.Default.Phone,
                    null,
                    modifier = Modifier.size(48.dp)
                )
            }
        }

    }
}


@Composable
fun AnimateAsStateDemo() {
    var blue by remember { mutableStateOf(true) }
//    val color by animateColorAsState(
//        if (blue) Blue else Red,
//        animationSpec = spring(Spring.StiffnessVeryLow)
//    )
    val scope = rememberCoroutineScope()
    var testContent by remember {
        mutableStateOf("Change Color ")
    }
    val color by animateColorAsState(
        if (blue) Blue else Red,
        animationSpec = spring(stiffness = Spring.StiffnessVeryLow),
        finishedListener = {
            blue = !blue//结束时更改值形成回弹反复动画
        }
    )

    DisposableEffect(Unit) {
        Log.e("ethan", "DisposableEffect")
        onDispose {
            Log.e("ethan", "onDispose")
            Log.e("ethan", "scope.isActive====" + scope.isActive)
        }
    }
    Column(Modifier.padding(16.dp)) {
        Text("AnimateAsStateDemo")
        Spacer(Modifier.height(16.dp))
        Button(
            onClick = {
                blue = !blue
                scope.launch {
//                    TestDelay().delayTestLaunch(1000) {
//                        testContent = it
//                    }
                }
            }
        ) {
            Text(testContent)
        }
        Spacer(Modifier.height(16.dp))
        Box(
            Modifier
                .size(20.dp, 20.dp)
                .background(color)
        )
    }
}

private sealed class BoxState(val color: Color, val size: Dp, val visibility: Boolean = true) {
    operator fun not() = if (this is Small) Large else Small

    object Small : BoxState(Blue, 30.dp, false)
    object Large : BoxState(Red, 80.dp, true)
}

//组合动画
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun UpdateTransitionDemo() {

    var boxState: BoxState by remember { mutableStateOf(BoxState.Small) }
    val transition = updateTransition(targetState = boxState, label = "")

    Column(Modifier.padding(16.dp)) {
        Text("组合多个动画，UpdateTransitionDemo")
        Spacer(Modifier.height(16.dp))
        Button(
            onClick = { boxState = !boxState }
        ) {
            Text("Change Color and size")
        }
        val color by transition.animateColor(label = "") {
            it.color
        }
        val size by transition.animateDp(transitionSpec = {
//            if (targetState == BoxState.Large) {
//                spring(stiffness = Spring.StiffnessVeryLow)
//            } else {
//                spring(stiffness = Spring.StiffnessHigh)
////                spring(stiffness = Spring.StiffnessVeryLow)
//            }
            tween(3000)
        }, label = "") {
            it.size
        }

        Spacer(Modifier.height(16.dp))
        Box(
            Modifier
                .size(size)
                .background(color)
        )
        transition.AnimatedVisibility(
            visible = { targetSelected -> boxState.visibility },
            enter = scaleIn() + expandVertically(expandFrom = Alignment.CenterVertically),
            exit = scaleOut() + shrinkVertically(shrinkTowards = Alignment.CenterVertically)
        ) {
            Column {
                Text(text = "It is fine today.")

            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedContentTest() {
    var count by remember { mutableStateOf(0) }
    Button(onClick = { count++ }) {
        Text("AnimatedContent")
    }
    AnimatedContent(
        targetState = count,
        transitionSpec = {
            // Compare the incoming number with the previous number.
            if (targetState > initialState) {
                // If the target number is larger, it slides up and fades in
                // while the initial (smaller) number slides up and fades out.
                slideInVertically { height -> height } + fadeIn() with
                        slideOutVertically { height -> -height } + fadeOut()
            } else {
                // If the target number is smaller, it slides down and fades in
                // while the initial number slides down and fades out.
                slideInVertically { height -> -height } + fadeIn() with
                        slideOutVertically { height -> height } + fadeOut()
            }.using(
                // Disable clipping since the faded slide-in/out should
                // be displayed out of bounds.
                SizeTransform(clip = true)
            )
        }
    ) { targetCount ->
        Text(text = "$targetCount")
    }
}


@Composable
fun AnimationAsStateAndAnimateTo(context: AppCompatActivity) {
    var flag by remember { mutableStateOf(false) }
    val animate = remember { Animatable(0.dp, Dp.VectorConverter) }
    // 通过协程触发 animateTo()
//    LaunchedEffect(key1 = flag) {
//        animate.animateTo(if (flag) 32.dp else 144.dp, animationSpec = keyframes {//插入关键帧
//            500.dp at 300//在300ms 时改变flag为500
//        })
//    }
    LaunchedEffect(key1 = flag) {
        animate.animateTo(if (flag) 32.dp else 144.dp, animationSpec = tween(1000), block = {
//            Log.e("ethan", "dp=====" + this.value)
        })

        //无限循环的 https://developer.android.com/jetpack/compose/animation?hl=zh-cn#rememberinfinitetransition
//        animate.animateTo(if (flag) 32.dp else 144.dp, animationSpec =   infiniteRepeatable(
//            animation = tween(3000, easing = LinearEasing),
//            repeatMode = RepeatMode.Restart
//        ), block = {
//            Log.e("ethan", "dp=====" + this.value)
//        })
    }


    val size2 by animateSizeAsState(
        if (flag) Size(40f, 40f) else Size(120f, 60f),
        tween(1000, easing = FastOutLinearInEasing), finishedListener = {
            // 可以设置动画结束的监听函数，回调动画结束时对应属性的目标值
            Log.e("ethan", "size animate finished with $it")
        }, label = ""
    )
    val size by animateDpAsState(if (flag) 32.dp else 144.dp, label = "") { valueOnAnimateEnd ->
        // 可以设置动画结束的监听函数，回调动画结束时对应属性的目标值
        Log.e("ethan", "size animate finished with $valueOnAnimateEnd")
    }
    Row(
        Modifier
            .size(Dp(size2.width), Dp(size2.height)) // size 在 animate 中取值
            .background(Color.Magenta)
            .clickable { flag = !flag }
    ) {
        Text(text = "AnimationAsStateAndAnimateTo", fontSize = 12.sp)
    }
    Spacer(modifier = Modifier.height(10.dp))
    Row(
        Modifier
            .size(animate.value) // size 在 animate 中取值
            .background(Color.Black)
            .clickable { flag = !flag }
    ) {
        Text(text = "AnimationAsStateAndAnimateTo", fontSize = 12.sp)
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class)
@Composable
fun SizeChangeAnimatedContent() {
    var expanded by remember { mutableStateOf(false) }
//    AnimatedVisibility(visible = expanded, enter = SizeTransform()) {
//
//    }
    AnimatedContent(
        targetState = expanded,
        transitionSpec = {
            fadeIn(animationSpec = tween(1150, delayMillis = 1150)) + scaleIn(tween(1150)) with
                    fadeOut(animationSpec = tween(1150)) using
                    SizeTransform { initialSize, targetSize ->
                        if (targetState) {
                            keyframes {
                                // Expand horizontally first.
                                IntSize(initialSize.width, initialSize.height) at 150
                                durationMillis = 1300
                            }
                        } else {
                            keyframes {
                                // Shrink vertically first.
                                IntSize(initialSize.width, initialSize.height) at 150
                                durationMillis = 1300
                            }
                        }
                    }
        }
    ) { targetExpanded ->
        if (targetExpanded) {
            Image(
                painter = painterResource(id = R.mipmap.jinzhu_icon),
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp)
                    .clickable { expanded = !expanded },
                contentDescription = "",
            )
        } else {
            Button(onClick = { expanded = !expanded }) {
                Text(text = "sizeChangeAnimatedContent点我看效果")
            }
        }

    }
}

@Composable
fun ExpandInAnimation() {
    var expanded by remember { mutableStateOf(true) }
    Button(onClick = { expanded = !expanded }) {
        Text(text = "expandInAnimation带裁效果")
    }
    // 展开，裁剪效果入场，expandFrom: 展开的方向, initialSize: 展开的初始位置，clip: 是否裁剪，默认是true
    AnimatedVisibility(
        visible = expanded,
        enter = expandIn(
            tween(1000),
            expandFrom = Alignment.CenterStart,
//            initialSize = { IntSize(it.width / 2, it.height / 2) },
            clip = true
        ),
        exit = shrinkOut(tween(1000), clip = true, shrinkTowards = Alignment.TopStart)
    ) {
        Image(
            painter = painterResource(id = R.mipmap.jinzhu_icon),
            modifier = Modifier
                .width(100.dp)
                .height(100.dp),
            contentDescription = "",
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimateVisibilityDemo() {
    var visible by remember { mutableStateOf(true) }
    Column(Modifier.padding(16.dp)) {
        Text("AnimateVisibilityDemo")
        Spacer(Modifier.height(16.dp))
        Button(
            onClick = { visible = !visible }
        ) {
            Text(text = if (visible) "Hide" else "Show")
        }

        Spacer(Modifier.height(16.dp))

        AnimatedVisibility(
            visible,

//            enter = expandVertically(),
//            exit = shrinkVertically()
            // By Default, `scaleOut` uses the center as its pivot point
            // the content will be shrinking both
            // in terms of scale and layout size towards the center.
            enter = scaleIn() + fadeIn(),
            exit = scaleOut() + fadeOut()
        ) {
            Box(
                Modifier
                    .size(80.dp)
                    .background(Blue)
            )
        }
    }
}

enum class UploadState {
    Normal,
    Uploading,
    Success
}

@Composable
fun UploadAnimation() {
    val originWidth = 180.dp
    val circleSize = 48.dp
    var uploadState by remember { mutableStateOf(UploadState.Normal) }
    var text by remember { mutableStateOf("Upload") }
    val updateTransient = updateTransition(targetState = uploadState, "update")
    val textAlpha by updateTransient.animateFloat(label = "", transitionSpec = {
        tween(durationMillis = 1000, easing = FastOutSlowInEasing)
    }) { target ->
        when (target) {
            UploadState.Normal -> {
                1f
            }

            UploadState.Success -> {
                1f
            }

            else -> {
                0f
            }
        }
    }
    val backgroundColor by updateTransient.animateColor(label = "color", transitionSpec = {
        tween(durationMillis = 2000, easing = FastOutSlowInEasing)
    }) { target ->
        when (target) {
            UploadState.Normal -> {
                Color.Blue
            }

            UploadState.Uploading -> {
                Color.Gray
            }

            UploadState.Success -> {
                Color.Red
            }
        }
    }
    val boxWidth by updateTransient.animateDp(label = "width", transitionSpec = {
        tween(durationMillis = 1000, easing = FastOutSlowInEasing)
    }) { target ->
        when (target) {
            UploadState.Normal -> {
                text = "upload"
                originWidth
            }

            UploadState.Uploading -> {
                circleSize
            }

            UploadState.Success -> {
                text = "success"
                originWidth
            }
        }
    }
    val progress by updateTransient.animateInt(label = "progress", transitionSpec = {
        tween(durationMillis = 3000, easing = FastOutSlowInEasing, delayMillis = 1000)
    }) { target ->
        if (target == UploadState.Uploading) {
            100
        } else {
            0
        }
    }
    val progressAlpha by updateTransient.animateFloat(label = "progressAlpha", transitionSpec = {
        tween(durationMillis = 1000, easing = FastOutSlowInEasing)
    }) { target ->
        if (target == UploadState.Uploading) {
            1f
        } else {
            0f
        }
    }
    // 界面布局
    Box(
        modifier = Modifier
            .padding(start = 10.dp, top = 10.dp)
            .width(originWidth),
        contentAlignment = Alignment.Center
    ) {
        // 按钮
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(circleSize / 2))
                .background(color = backgroundColor)
                .size(boxWidth, circleSize)
                .clickable {
                    uploadState = when (uploadState) {
                        UploadState.Normal -> {
                            UploadState.Uploading
                        }

                        UploadState.Uploading -> {
                            UploadState.Success
                        }

                        UploadState.Success -> {
                            UploadState.Normal
                        }

                    }
                },
            contentAlignment = Alignment.Center,
        ) {
            // 进度
            Box(
                modifier = Modifier
                    .size(circleSize)
                    .clip(ArcShape(progress, listener = {
                        if (it == 100) {
                            uploadState = UploadState.Success
                        }
                    }))
                    .alpha(progressAlpha)
                    .background(Color.Blue)
            )
            // 白色蒙版
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(boxWidth))
                    .alpha(progressAlpha)
                    .background(Color.White)
            )
            // 文字
            Text(text, color = Color.White, modifier = Modifier.alpha(textAlpha))
        }
    }
}

@Composable
fun content(value: Int) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "屏幕内容区域_有侧边栏$value")
        }
    }
}

@Composable
fun Typewriter(
    delayMill: Long,
    onclick: () -> Unit = {},
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = LocalTextStyle.current
) {

    val scop = rememberCoroutineScope()
    var content by remember {
        mutableStateOf(text)
    }
    Text(text = content, modifier = Modifier
        .clickable {
            scop.launch {
                content = ""
                text.forEachIndexed { index, c ->
                    delay(delayMill)
                    content += c
                }
            }
            onclick.invoke()
        }
        .then(modifier))

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TTPreview() {
    LightDarkTheme {
//        drawContent(imgId = R.drawable.ic_launcher_background)
    }
}
