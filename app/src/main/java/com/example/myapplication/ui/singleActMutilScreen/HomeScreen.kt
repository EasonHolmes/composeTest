package com.example.myapplication.ui.singleActMutilScreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.example.myapplication.Logutils
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    nextPageAction: () -> Unit,
) {
    Logutils.e("HomeScreen")
    val pagerState = rememberPagerState(
        initialPage = 0, pageCount = { 3 }
    )
//    Scaffold(
//        bottomBar = {
//            BottomNav(
//                bottomNumber = mutableListOf(
//                    BottomNavRoute.Home.tabName,
//                    BottomNavRoute.Category.tabName,
//                    BottomNavRoute.Profile.tabName
//                ), pagerState = pagerState
//            )
//        },
//    ) { paddingValues ->
//        paddingValues
//        Logutils.e("Scaffold")
        Column(modifier = Modifier.fillMaxSize()) {
            HorizontalPager(
                state = pagerState,
                beyondBoundsPageCount = 4
            ) { page ->
                when (page) {
                    0 -> Tab1(content = "tab1", nextPageAction )
                    1 -> Tab2(content = "tab2", nextPageAction)
                    2 -> Tab3(content = "tab3")
                }
            }
        }
//    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BottomNav(
    bottomNumber: List<String>,
    pagerState: androidx.compose.foundation.pager.PagerState
) {
    val scope = rememberCoroutineScope()
    //不能使用pagerState.currentPage，因为引用会引起向上刷新
    var currentPage by remember {
        mutableIntStateOf(0)
    }
    val bottomIcon =
        listOf(Icons.Default.Home, Icons.Default.Clear, Icons.Default.Call, Icons.Default.Email)
    BottomNavigation {
        bottomNumber.forEachIndexed { index, contentText ->
            BottomNavigationItem(
                modifier = Modifier.background(Color.Black),
                selected = currentPage == index,
                onClick = {
                    currentPage = index;
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                }, label = {
                    Text(
                        text = contentText,
                        color = if (currentPage == index) Color.Blue else Color.White
                    )
                }, icon = {
//                            if (checkIndex == index) {
//                                BadgeBox(badgeContent = {
                    //显示消息气泡99+
//                                    Text(text = "99+")
//                                }, contentColor = Color.White, backgroundColor = Color.Red) {
//                                    Icon(imageVector = bottomIcon[index], contentDescription = null)
//                                }
//                            } else {
                    Icon(imageVector = bottomIcon[index], contentDescription = null)
//                            }
                }, selectedContentColor = Color.Red, unselectedContentColor = Color.White
            )
        }
    }
}


@Composable
fun Tab1(content: String,     nextPageAction: () -> Unit,
) {
    Logutils.e("Tab1")
//    Column(modifier = Modifier.fillMaxSize()) {
//        Text(text = content, modifier = Modifier.clickable {
//            navCtrl.navigate("details/dfadsfsadf/aaaa") {
//                Logutils.e("id==" + navCtrl.graph.findStartDestination().id)
//                popUpTo(navCtrl.graph.findStartDestination().id) {
//                    saveState = true
//                }
//                launchSingleTop = true
//                restoreState = true
//            }
//        })
//    }
//    ContentUI(navCtrl = navCtrl)
    ContentUI(nextPageAction)
}

@Composable
fun Tab2(content: String,
         nextPageAction: () -> Unit,
         ) {
    Logutils.e("Tab2")
    ContentUI(nextPageAction)

}

@Composable
fun Tab3(content: String) {
    Logutils.e("Tab3")
    Column {
        Text(text = content)
    }

}

@Composable
fun ContentUI(
    nextPageAction: () -> Unit,
) {
    var testTxt by rememberSaveable {
        mutableStateOf("test")
    }
    Column {
        //必传
        Button(onClick = {
            //这里引用navCtrl会引起向上刷新需要看看怎么解决
//          NagationTo.naTo()

//            navCtrl.navigate("details/dfadsfsadf/aaaa") {
//                Logutils.e("id==" + navCtrl.graph.findStartDestination().id)
//                popUpTo(navCtrl.graph.findStartDestination().id) {
//                    saveState = true
//                }
//                launchSingleTop = true
//                restoreState = true
//
//            }
            testTxt = "aaaaaa"
//            navCtrl.navigate("details/dfadsfsadf/aaaa")
//            navCtrl.navigateSingleTopTo("details/dfadsfsadf/aaaa")
            nextPageAction.invoke()
        }) {
            Text(text = "button$testTxt")
        }
//        Text(
//            text = navCtrl.currentBackStackEntry?.savedStateHandle!!.getLiveData(
//                "back",
//                ""
//            ).value!!
//        )
//        //选传
//        Button(onClick = { navCtrl.navigate("details2?userId=fffff?itemId=aaaaaaa") }) {
//            Text(text = "button")
//        }
//        Text(
//            text = navCtrl.currentBackStackEntry?.savedStateHandle!!.getLiveData(
//                "back",
//                ""
//            ).value!!
//        )
    }
}