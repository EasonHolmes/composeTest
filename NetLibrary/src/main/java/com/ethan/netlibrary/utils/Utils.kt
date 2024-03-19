package com.ethan.netlibrary.utils

var MEMORY_TOKEN = ""

//private const val BASEURL= "BASEURL"
fun getCacheToken(): String {
    return if (MEMORY_TOKEN.isEmpty()) {
        InlandMmkvUtils.INSTANCES.getString("TOKEN")
//    return getLocalToken() ?: ""
    } else {
        MEMORY_TOKEN
    }
}

//fun getUrl()= if (DEBUG_ANABLE) TESTURL else ONLINETURL

fun saveCacheToken(token: String) {
    //先用外插的
    MEMORY_TOKEN = token
    InlandMmkvUtils.INSTANCES.putString("TOKEN", token)
//    saveToken(token)
}
