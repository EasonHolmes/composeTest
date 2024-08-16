package com.example.myapplication.ui.singleActMutilScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.myapplication.Logutils
import com.example.myapplication.ui.vm.TestViewModel

@Composable
fun DetailsScreen2(
                           itemId: String?, userId: String?) {
    Column(modifier = Modifier
        .fillMaxSize()
        .clickable {
//            navCtrl.previousBackStackEntry?.savedStateHandle?.set(
//                "back",
//                "backResult2"
//            )
//            navCtrl.popBackStack()
//
        }) {
        Text(text = "item===${itemId}")
        Text(text = "item===${userId}")
    }
}