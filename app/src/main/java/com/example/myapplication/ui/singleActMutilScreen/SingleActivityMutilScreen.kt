package com.example.myapplication.ui.singleActMutilScreen

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.compose.compiler.plugins.kotlin.EmptyFunctionMetrics.composable
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.Logutils
import com.example.myapplication.ui.BaseActivity
import com.example.myapplication.ui.vm.TestViewModel
import kotlinx.coroutines.launch

object RouteName {
    const val HOME = "home"
    const val CATEGORY = "category"
    const val DETAIL = "DETAIL"
    const val PROFILE = "profile"
    const val WEB_VIEW = "web_view"
    const val LOGIN = "login"
    const val ARTICLE_SEARCH = "article_search"
}

sealed class BottomNavRoute(
    var routeName: String,
    var tabName: String,
    var icon: ImageVector
) {
    object Home : BottomNavRoute(RouteName.HOME, "home", Icons.Default.Home)
    object Category : BottomNavRoute(RouteName.CATEGORY, "category", Icons.Default.Menu)
    object Profile : BottomNavRoute(RouteName.PROFILE, "profile", Icons.Default.Person)
}


//object NagationTo{
//    lateinit var navCtrl : NavHostController
//
//    fun naTo(){
//        navCtrl.navigate("details/dfadsfsadf/aaaa") {
//            Logutils.e("id==" + navCtrl.graph.findStartDestination().id)
//            popUpTo(navCtrl.graph.findStartDestination().id) {
//                saveState = true
//            }
//            launchSingleTop = true
//            restoreState = true
//        }
//    }
//}

class SingleActivityMutilScreen : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun ContentView() {
//        val navCtrl = rememberNavController()
//
//        androidx.compose.material3.Scaffold { paddingValues ->
//            paddingValues
//            Logutils.e("ffffff" + paddingValues.toString())
//            NavHost(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(bottom = paddingValues.calculateBottomPadding()),
//                navController = navCtrl, startDestination = RouteName.HOME,
//            ) {
//                // 定义导航目的地
//                composable(RouteName.HOME) {
//                    HomeScreen(
//                        nextPageAction = {
//                            navCtrl.navigate("${RouteName.DETAIL}/dfadsfsadf/aaaa")
//                        },
//                    )
//                }
////            composable("details/{itemId}") { backStackEntry ->
////                val itemId = backStackEntry.arguments?.getString("itemId")
////                DetailsScreen(navController, itemId)
////            }
//                composable(
//                    "${RouteName.DETAIL}/{itemId}/{userId}",//必传参方式，有漏传的会报错
//                    arguments = listOf(navArgument("itemId") { type = NavType.StringType },
//                        navArgument("userId") { type = NavType.StringType })
//                ) { backStackEntry ->
//                    val itemId = backStackEntry.arguments?.getString("itemId")
//                    val userId = backStackEntry.arguments?.getString("userId")
//                    DetailsScreen(userId,  navCtrl, TestViewModel(), nextPageAction = {
//                        navCtrl.navigate("details/dfadsfsadf/aaaa")
//                    })
//                }
//                composable(
//                    "details2?userId={userId}?itemId={itemId}",//可选传的
//                    arguments = listOf(navArgument("userId") { defaultValue = "user1234" },
//                        navArgument("itemId") { defaultValue = "111111" }
//                    )
//                ) { backStackEntry ->
//                    DetailsScreen2(
//                        backStackEntry.arguments?.getString("userId"),
//                        backStackEntry.arguments?.getString("itemId")
//                    )
//                }
//            }
//        }
        CupcakeApp()

    }

    override fun needTopbar(): Boolean {
        return false
    }
}


fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        Logutils.e("findStartDestination_Id==" + this@navigateSingleTopTo.graph.findStartDestination().id)
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }


///**
// * TabLayout
// */
//@Composable
//fun TextTabBar(
//    index: Int = 0,
//    tabTexts: List<TabTitle>,
//    modifier: Modifier = Modifier,
//    contentAlign: Alignment = Alignment.Center,
//    bgColor: Color = Color.Black,
//    contentColor: Color = Color.White,
//    onTabSelected: ((index: Int) -> Unit)? = null
//) {
//    Box(
//        modifier = modifier
//            .fillMaxWidth()
//            .height(54.dp)
//            .background(bgColor)
//            .horizontalScroll(state = rememberScrollState())
//    ) {
//        Row(
//            modifier = Modifier
//                .align(contentAlign)
//        ) {
//            tabTexts.forEachIndexed { i, tabTitle ->
//                Text(
//                    text = tabTitle.text,
//                    fontSize = if (index == i) 20.sp else 15.sp,
//                    fontWeight = if (index == i) FontWeight.SemiBold else FontWeight.Normal,
//                    modifier = Modifier
//                        .align(Alignment.CenterVertically)
//                        .padding(horizontal = 10.dp)
//                        .clickable {
//                            onTabSelected?.invoke(i)
//                        },
//                    color = contentColor
//                )
//            }
//        }
//    }
//}
//
//data class TabTitle(
//    val id: Int,
//    val text: String,
//    var cachePosition: Int = 0,
//    var selected: Boolean = false
//)

