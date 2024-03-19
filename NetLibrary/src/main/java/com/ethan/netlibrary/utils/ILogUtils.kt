package com.ethan.netlibrary.utils

import android.util.Log

const val ETAG = "rxinland"

/**
 * Created by cuiyang on 16/6/2.
 */
object ILogUtils {
    private var isDebug = false


    @JvmStatic
    fun setDbug(debug: Boolean) {
        isDebug = debug
    }

    fun getDebug() = isDebug


    @JvmStatic
    fun v(msg: String, tag: String = ETAG) {
        if (isDebug) {
            Log.v(tag, msg)
        }
    }

    @JvmStatic
    fun e(msg: String, tag: String = ETAG) {
        if (isDebug) {
            Log.e(tag, msg)
        }
    }

    @JvmStatic
    fun d(msg: String, tag: String = ETAG) {
        if (isDebug) {
            Log.d(tag, msg)
        }
    }

}
