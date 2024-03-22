package com.example.myapplication.ui

import android.os.Bundle
import android.view.MotionEvent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.myapplication.R
import com.example.myapplication.entity.MolesEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random

class WhackMoleActivity : BaseActivity() {

    private val molesList by lazy {
        val list = mutableStateListOf<MolesEntity>()
        for (i in 1..5) {
            list.add(MolesEntity())
        }
        list
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        readyStartChane()
    }

    @Composable
    override fun ContentView() {
        MoleGrid()

    }

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    private fun MoleGrid() {
        LazyVerticalGrid(columns = GridCells.Fixed(5), content = {
            itemsIndexed(items = molesList, itemContent = { index: Int, item: MolesEntity ->
                var isClick by remember {
                    mutableStateOf(false)
                }
                Box(contentAlignment = Alignment.Center) {
                    Image(
                        painter = painterResource(id = item.icon),
                        contentDescription = "",
                        modifier = Modifier
                            .pointerInteropFilter {
                                isClick = it.action == MotionEvent.ACTION_DOWN
                                if (it.action == MotionEvent.ACTION_DOWN && !item.isClicked && item.icon == R.mipmap.mole_icon) {
                                    molesList[index] = molesList[index].copy(
                                        isClicked = true,
                                        icon = R.mipmap.caodi_icon
                                    )
                                }
                                true
                            }
                            .size(80.dp))
                    if (isClick) {
                        Image(
                            painter = painterResource(id = R.mipmap.whack_icon),
                            contentDescription = "", modifier = Modifier.size(80.dp)
                        )
                    }
                }
            })
        })
    }

    private fun readyStartChane() {
        lifecycleScope.launch(Dispatchers.IO) {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                startChange()
            }
        }
    }

    private suspend fun startChange() {
        if (molesList.any { !it.isClicked }) {
            val index =randomIndex()
            molesList[index] = molesList[index].copy(icon = R.mipmap.mole_icon)
            delay(1000)
            molesList[index] = molesList[index].copy(icon = R.mipmap.caodi_icon)
            startChange()
        } else {
            withContext(Dispatchers.Main) {
                Toast.makeText(this@WhackMoleActivity, "isDone", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun randomIndex(): Int {
        while (true) {
            val index = Random.nextInt(0, molesList.size)
            if (!molesList[index].isClicked) {
                return index
            }
        }
    }
}