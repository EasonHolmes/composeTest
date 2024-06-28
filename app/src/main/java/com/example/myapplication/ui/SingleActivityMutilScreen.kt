package com.example.myapplication.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.ui.vm.TestViewModel

class SingleActivityMutilScreen : BaseActivity() {

    private lateinit var navController: NavHostController


    @Composable
    override fun ContentView() {
        navController = rememberNavController()
        NavHost(navController, startDestination = "home") {
            // 定义导航目的地
            composable("home") { HomeScreen(navController) }
//            composable("details/{itemId}") { backStackEntry ->
//                val itemId = backStackEntry.arguments?.getString("itemId")
//                DetailsScreen(navController, itemId)
//            }
            composable(
                "details/{itemId}/{userId}",//必传参方式，有漏传的会报错
                arguments = listOf(navArgument("itemId") { type = NavType.StringType },
                    navArgument("userId") { type = NavType.StringType })
            ) { backStackEntry ->
                val itemId = backStackEntry.arguments?.getString("itemId")
                val userId = backStackEntry.arguments?.getString("userId")
                DetailsScreen(navController, itemId, userId, TestViewModel())
            }
            composable(
                "details2?userId={userId}?itemId={itemId}",//可选传的
                arguments = listOf(navArgument("userId") { defaultValue = "user1234" },
                    navArgument("itemId") { defaultValue = "111111" }
                )
            ) { backStackEntry ->
                DetailsScreen2(
                    navController,
                    backStackEntry.arguments?.getString("userId"),
                    backStackEntry.arguments?.getString("itemId")
                )
            }
        }
    }

    override fun needTopbar(): Boolean {
        return false
    }

    @Composable
    private fun HomeScreen(navController: NavController) {
        Column {
            Button(onClick = { navController.navigate("details/dfadsfsadf/aaaa") }) {
                Text(text = "button")
            }
            Text(
                text = navController.currentBackStackEntry?.savedStateHandle!!.getLiveData(
                    "back",
                    ""
                ).value!!
            )
            Button(onClick = { navController.navigate("details2?userId=fffff?itemId=aaaaaaa") }) {
                Text(text = "button")
            }
            Text(
                text = navController.currentBackStackEntry?.savedStateHandle!!.getLiveData(
                    "back",
                    ""
                ).value!!
            )
        }
    }

    @Composable
    fun DetailsScreen(
        navController: NavController,
        itemId: String?,
        userId: String?,
        testViewModel: TestViewModel
    ) {
        val dd by testViewModel.uiEvent.collectAsStateWithLifecycle()

        Column(modifier = Modifier.clickable {
            navController.previousBackStackEntry?.savedStateHandle?.set("back", "backResult")
            navController.popBackStack()
        }) {
            Text(text = "item===${dd.content}")
            Text(text = "item===${userId}")
            Text(text = "item===${userId}")
            Text(text = "item===${userId}")
            Text(text = "item===${userId}")
            Text(text = "item===${userId}")
            Text(text = "item===${userId}")
            Text(text = "item===${userId}")
            Text(text = "item===${userId}")
            Text(text = "item===${userId}")
            Text(text = "item===${userId}")
            Text(text = "item===${userId}")
            Text(text = "item===${userId}")
            Text(text = "item===${userId}")
        }
        LaunchedEffect(key1 = dd) {
            testViewModel.changeData()
        }
    }

    @Composable
    fun DetailsScreen2(navController: NavController, itemId: String?, userId: String?) {
        Column(modifier = Modifier.clickable {
            navController.previousBackStackEntry?.savedStateHandle?.set("back", "backResult2")
            navController.popBackStack()
        }) {
            Text(text = "item===${itemId}")
            Text(text = "item===${userId}")
        }
    }

}