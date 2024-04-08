package com.example.myapplication

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
//import com.at.ic.Ihelper
//import com.ethan.mylibrary.Empty2Activity
//import com.ethan.mylibrary.EmptyActivity
import com.tencent.mmkv.MMKV


/**
 * Created by Ethan Cui on 2023/2/23
 */
class MainApplication: Application() {
//    override fun onCreate() {
//        super.onCreate()
//        //隐藏icon
////        s(this, EmptyActivity::class.java.getName())
////        IcUtils.icon(this,EmptyActivity::class.java.name)
//    }
    override fun onCreate() {
        super.onCreate()
//        ContextCompat.startForegroundService(
//            this, Intent(
//                this,
//                CFNService::class.java
//            )
//        )
//    Ihelper.helper1(this,Empty2Activity::class.java.name,EmptyActivity::class.java.name)
        MMKV.initialize(this)
    }


    override fun attachBaseContext(context: Context?) {
        super.attachBaseContext(context)
//        MultiProcess.start(this, CFNService::class.java)

    }

}