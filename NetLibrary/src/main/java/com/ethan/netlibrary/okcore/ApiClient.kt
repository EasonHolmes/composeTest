package com.ethan.netlibrary.okcore

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody

/**
 * Created by cuiyang on 16/6/1.
 */
private var  CHANGE_BASE_URL: String = ""

fun getRequestBody(json: String?): RequestBody {
    return RequestBody.create(
        "Content-Type: application/json; charset=utf-8".toMediaTypeOrNull(),
        json!!
    )
}
//用到的method再反射缓存 true为在一开始就全部反射缓存
//val retrofit: Retrofit by lazy {
//    Retrofit.Builder()
//        .client(getOkhttpClient())
////        .baseUrl(if (ILogUtils.getDebug()) "http://172.30.179.60:39698/api/" else "http://172.30.179.60:39698/api/")
//        .addConverterFactory(GsonConverterFactory.create())
////            .addCallAdapterFactory(rxJavaCallAdapterFactory) //用到的method再反射缓存 true为在一开始就全部反射缓存
//        .addCallAdapterFactory(CoroutineCallAdapterFactory())
//        .validateEagerly(false)
//        .build()
//}
