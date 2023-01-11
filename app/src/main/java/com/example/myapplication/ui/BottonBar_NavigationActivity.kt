package com.example.myapplication.ui

import android.provider.SyncStateContract
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

/**
 * Created by Ethan Cui on 2023/1/11
 */
class NavigationActivity : BaseActivity() {
    val BottomNavItems = listOf(
        BottomNavItem("home",Icons.Default.Home,"home"),
        BottomNavItem("clear",Icons.Default.Clear,"clear"),
        BottomNavItem("call",Icons.Default.Call,"call"),
    )
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun ContentView() {
//        var checkIndex by remember {
//            mutableStateOf(0)
//        }
//        val bottomNumber = listOf("首页", "清除", "电话")
//        val bottomIcon =
//            listOf(Icons.Default.Home, Icons.Default.Clear, Icons.Default.Call, Icons.Default.Email)
        val navController = rememberNavController()
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        Scaffold(bottomBar = {
            BottomNavigationBar(navController = navController)
        }) { paddingValues ->
            NavHostContainer(navController = navController, padding = paddingValues)
        }
    }

    @Composable
    fun BottomNavigationBar(navController: NavController) {
        BottomNavigation(
            backgroundColor = Color.White,
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            BottomNavItems.forEach { navItem ->
                BottomNavigationItem(
                    selected = currentRoute == navItem.route,
                    onClick = {
                        navController.navigate(navItem.route)
                    },
                    icon = {
                        Icon(imageVector = navItem.icon, contentDescription = navItem.label)
                    },
                    label = {
                        Text(text = navItem.label, color = if (currentRoute == navItem.route) Color.Black else Color.Gray)
                    },selectedContentColor = Color.Black, unselectedContentColor = Color.Gray
                )
            }
        }
    }
    @Composable
    fun NavHostContainer(
        navController: NavHostController,
        padding: PaddingValues
    ) {
        NavHost(
            navController = navController,
            // set the start destination as home
            startDestination = BottomNavItems[0].route,
            // Set the padding provided by scaffold
            modifier = Modifier.padding(paddingValues = padding),
            builder = {
                composable(BottomNavItems[0].route) {
                    Text(text = "page1")
                }
                composable(BottomNavItems[1].route) {
                    Text(text = "page2")
                }
                composable(BottomNavItems[2].route) {
                    Text(text = "page3")
                }
            }
        )
    }
}

data class BottomNavItem(
    val label: String,
    val icon: ImageVector,
    val route:String,
)