package com.inland.clibrary.net.okcore

import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit


/**
 * Created by Ethan Cui on 2022/3/22
 */
class OkHttpClientSingle private constructor() {
    companion object {
        fun getOkhttpClient(): OkHttpClient {
            val okHttpClient: OkHttpClient by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
                val okHttpBuilder = OkHttpClient.Builder()
                val dispatcher = Dispatcher()
//                dispatcher.maxRequests = 1//最大一个并发
                okHttpBuilder.dispatcher(dispatcher)
                okHttpBuilder.readTimeout(20, TimeUnit.SECONDS)
                okHttpBuilder.writeTimeout(20, TimeUnit.SECONDS)
                okHttpBuilder.connectTimeout(20, TimeUnit.SECONDS)
                okHttpBuilder.retryOnConnectionFailure(false)
                //okHttpBuilder.retryOnConnectionFailure(false);//设置出现错误是否进行重新连接。
//                okHttpBuilder.addInterceptor(EncryptJsonInterceptor())//加密拦截器
//                okHttpBuilder.addInterceptor(RefreshTokenInterceptor())
                okHttpBuilder.build()
            }
            return okHttpClient
        }
    }
}
