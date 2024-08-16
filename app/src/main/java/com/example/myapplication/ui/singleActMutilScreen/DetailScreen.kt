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
fun DetailsScreen(
    userId: String?,
    navCtrl: NavHostController,
    testViewModel: TestViewModel,
    nextPageAction: () -> Unit,
    ) {
    Logutils.e("afafafafafaf")
    val dd by testViewModel.uiEvent.collectAsStateWithLifecycle()

    Column(modifier = Modifier
        .fillMaxSize()
        .clickable {
//                navCtrl.previousBackStackEntry?.savedStateHandle?.set(
//                    "back",
//                    "backResult"
//                )
//                navCtrl.popBackStack()
//            navCtrl.navigateSingleTopTo("details2?userId=fffff?itemId=aaaaaaa")
            nextPageAction.invoke()
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