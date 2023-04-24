package com.example.myapplication

import android.app.Application
import com.ethan.mylibrary.EmptyActivity
import com.ethan.mylibrary.IcUtils
import com.ethan.mylibrary.IconUtils.g
import com.ethan.mylibrary.IconUtils.h
import com.ethan.mylibrary.IconUtils.s


/**
 * Created by Ethan Cui on 2023/2/23
 */
class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        //隐藏icon
//        s(this, EmptyActivity::class.java.getName())
//        IcUtils.icon(this,EmptyActivity::class.java.name)
    }
}