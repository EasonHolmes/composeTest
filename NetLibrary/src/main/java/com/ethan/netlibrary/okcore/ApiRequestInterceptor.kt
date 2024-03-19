package com.ethan.netlibrary.okcore

import android.app.Application
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.ethan.netlibrary.core.cache.NetCode
import com.ethan.netlibrary.core.cache.getCacheAndroidId
import com.ethan.netlibrary.core.cache.getCoreKey
import com.ethan.netlibrary.core.cache.getPackageName
import com.ethan.netlibrary.model.ApiRequestBody
import com.ethan.netlibrary.model.KeyModel
import com.ethan.netlibrary.utils.EncryptUtils
import com.ethan.netlibrary.utils.ILogUtils
import com.ethan.netlibrary.utils.MD5Util
import com.ethan.netlibrary.utils.decodeAESCompress
import com.ethan.netlibrary.utils.getCacheToken
import com.ethan.netlibrary.utils.saveCacheToken
import com.inland.clibrary.net.okcore.HttpData
import com.inland.clibrary.net.okcore.OkHttpClientSingle
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.NonCancellable.cancel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.isActive
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okio.IOException

/**
 * Created by Ethan Cui on 2022/3/22
 */
//suspend fun <T> NetViewModel.flowRequest(
//    request: suspend ApiService.() -> HttpData<T>?,
//): Flow<HttpData<T>> {
//    return flow {
//        val response = request(apiService) ?: throw IllegalArgumentException("数据非法，获取响应数据为空")
//        response.throwAPIException();
//        emit(response)
//    }.map {
//        if (!it.isSuccess && it.message.isNotEmpty())
//            resposenCodeOptionEvent.values = it.code
//        it.isSuccess
//        it
//    }.flowOn(Dispatchers.IO)
////        .catch {
////            apiExceptionEvent.values = it
////            CCLogUtils.e(NetViewModel::class.java,"catch=====" + Thread.currentThread().name)
////        }
//        .onCompletion { cause ->
//            ILogUtils.e("onCompletion=====" + Thread.currentThread().name)
//            ILogUtils.e("onCompletion=====" + cause.toString())
//        }
//}
//suspend fun <T> BubbleConnector.retrofitFlowRequest(
//    actionName: String,
//    requestId: String,
//    request: suspend ApiService.() -> ResponseBody?,
//): Flow<HttpData<String>> {
//    return flow {
//        val response = request(apiService) ?: throw IllegalArgumentException("数据非法，获取响应数据为空")
//        emit(response)
//    }.map {
//        //解密
//        val responData = it.decodeAESCompress(actionName,getCoreKey())
//        val json = EncryptUtils.decodeAESCompress(actionName,
//            MD5Util.md5Hex(
//                java.lang.String.format(
//                    "%s-%s-%s",
//                    LOGIN_ACTION,
//                    requestId,
//                    getPackageName()
//                )
//            ),
//            responData.data
//        )
//        responData.data = json
//        ILogUtils.e("responseModel===${responData.toString()}")
//        responData
//    }.flowOn(Dispatchers.IO)
//        .onCompletion { cause ->
//            ILogUtils.e("onCompletion=====" + Thread.currentThread().name)
//            ILogUtils.e("onCompletion=====" + cause.toString())
//        }
//}

/**
 * okhttp's interceptor 进行统一的加密操作
 * 因为是字符串式交互并且路由以actionName 不以url，所以retrofit会显得用处不大
 */
//suspend fun retrofitFlowRequest(
//    actionName: String,
//    requestId: String,
//    request: suspend ApiService.() -> ResponseBody?,
//): Flow<HttpData<String>> {
//    return flow {
//        val response = request(apiService) ?: throw IllegalArgumentException("数据非法，获取响应数据为空")
//        emit(response)
//    }.map {
//        //解密
//        val responData = it.decodeAESCompress(actionName,getCoreKey())
//        val json = EncryptUtils.decodeAESCompress(actionName,
//            MD5Util.md5Hex(
//                java.lang.String.format(
//                    "%s-%s-%s",
//                    actionName,
//                    requestId,
//                    getPackageName()
//                )
//            ),
//            responData.data
//        )
//        responData.data = json
//        ILogUtils.e("responseModel===${responData}")
//        responData
//    }.flowOn(Dispatchers.IO)
//        .onCompletion { cause ->
//            ILogUtils.e("onCompletion=====" + Thread.currentThread().name)
//            ILogUtils.e("onCompletion=====" + cause.toString())
//        }
//}
val connectionUrl = if (ILogUtils.getDebug()) "TESTURL" else "ONLINETURL"

private fun decodeResponseData(
    actionName: String,
    requestId: String,
    response: Response
): HttpData<String> {
    val responData = response.body!!.decodeAESCompress(actionName, getCoreKey())
    val json = EncryptUtils.decodeAESCompress(
        actionName,
        MD5Util.md5Hex(
            java.lang.String.format(
                "%s-%s-%s",
                actionName,
                requestId,
                getPackageName()
            )
        ),
        responData.data
    )
    responData.data = json
    return responData
}


private fun makeRequest(
    actionName: String,
    requestId: String,
    keyModel: KeyModel?
): Request {
    val builder = ApiRequestBody.builder()
        .action(actionName)
    if (keyModel != null) {
        builder.data(keyModel.map)
    }
//    ILogUtils.e("okhttpFlowRequest=====" + Thread.currentThread().name)
    ILogUtils.e("requestAction===" + actionName + "===requestModel===" + keyModel?.map.toString())
//    NetBiEventModel.track(actionName = actionName, requestModel = keyModel, start = true)
    val apiRequestBody =
        builder
            .token(getCacheToken())
            .packageName(getPackageName())
            .requestId(requestId).build()
    val call = Request.Builder()
        .url(connectionUrl)
        .post(
            apiRequestBody.toEncryptString(getCoreKey())
                .toRequestBody("text/plain".toMediaTypeOrNull())
        )
        .build()
    return call
}

private fun loginRequestBody(requestId: String): Request {
    return Request.Builder()
        .url(connectionUrl)
        .post(
            ApiRequestBody.builder()
                .action("LOGIN_ACTION")
                .packageName(getPackageName())
                .requestId(requestId)
                .data(
//            if (wxLogined()) {
//                KeyModel.create()
//                    .of("loginId", getCacheWxOpenId())
//                    .of("loginType", WECHAT)
//                    .of("deviceId", getCacheAndroidId())
//                    .of("timeZone", 8).map
//            } else {
                    KeyModel.create()
                        .of("deviceId", getCacheAndroidId())
                        .of("timeZone", 8).map
//            }
                ).build().toEncryptString(getCoreKey())
                .toRequestBody("text/plain".toMediaTypeOrNull())
        )
        .build()
}
///**
// * body加密直接在这里进行不需要走拦截器
// * 由于每次请求都是相同URL，只是用actionName用路由，所以不使用retrofit,直接使用okhttp
// */
@OptIn(ExperimentalCoroutinesApi::class)
suspend fun <T> okhttpFlowRequest(
    actionName: String,
//    clazz: Class<T>,
    keyModel: KeyModel? = null
): Flow<HttpData<String>> = callbackFlow {
    val requestId = System.currentTimeMillis().toString()
    val request =
        OkHttpClientSingle.getOkhttpClient().newCall(makeRequest(actionName, requestId, keyModel))
    request.enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
//            NetBiEventModel.track(actionName, false, message = e.toString())
//            cancel(e.toString())
            cancel(e.toString())
        }

        override fun onResponse(call: Call, response: Response) {
            if (response.code == 200 && response.body != null) {
                val httpData = decodeResponseData(actionName, requestId, response)
                when {
                    httpData.isSuccess -> {

                        success(httpData)
                    }

                    httpData.code == NetCode.INVALID_LOGIN_TOKEN.code -> {
                        // todo token过期自动重试
                        //为什么token不放在header里 body搞的很难受
//                        val requestIDTokent = System.currentTimeMillis().toString()
//                        val requestToken = loginRequestBody(requestIDTokent)
//                        ILogUtils.e("requestUrl = $connectionUrl")
//                        val call = Request.Builder()
//                            .url(connectionUrl)
//                            .post(
//                                requestToken.toEncryptString(getCoreKey())
//                                    .toRequestBody("text/plain".toMediaTypeOrNull())
//                            )
//                            .build()
//                        refreshRequest(requestIDTokent, call)
                        refreshTokenRequest(actionName,keyModel, onSuccess = {httpData: HttpData<String> ->

                        }, onFailure = {str->

                        })
                    }

                    httpData.code == NetCode.NOT_MODEL_CONFIG.code -> {
                        cancel("code=" + httpData.code + "==message==" + httpData.message)
                    }

                    else -> {
                        cancel(httpData.message)
                    }
                }
            } else {
//                cancel(response.code.toString() + "返回内容为空")
                cancel()
            }
        }


        private fun success(httpData: HttpData<String>) {
            ILogUtils.e("responseAction==" + actionName + "===responseModel===${httpData}")
            //等服务端统一处理了失败返回体再改 ，先这么容错着吧
            if (httpData.data.toString().contains("error")) {
//                cancel(actionName+"操作失败")
                cancel()
            } else {
                if (isActive) {
                    trySend(httpData)
                    close()
                }
            }
        }


    })
    awaitClose {
        ILogUtils.e("close connection $actionName")
        if (isActive) {
            request.cancel()
        }
//        close()
    }
}

private fun refreshTokenRequest(
    actionName: String,
    keyModel: KeyModel?,
    onSuccess: (HttpData<String>) -> Unit,
    onFailure: (String) -> Unit
) {
    val requestId = System.currentTimeMillis().toString()
    OkHttpClientSingle.getOkhttpClient().newCall(loginRequestBody(requestId))
        .enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
//                        NetBiEventModel.track(actionName, false, message = e.message.toString())
                ILogUtils.e("refreshTokenError===$e")
//                        cancel(e.message.toString())
                onFailure(e.message.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.body != null) {
                    val httpData =
                        decodeResponseData("LOGIN_ACTION", requestId, response)
                    if (httpData.isSuccess) {
                        try {
                            //save token
                            saveCacheToken(
                                org.json.JSONObject(httpData.data).getString("token")
                            )
                        } catch (e: Exception) {
                            onFailure(e.message.toString())
                            return
                        }
                        val refreshRequestId = System.currentTimeMillis().toString()
//                        val builderRefresh = ApiRequestBody.builder()
//                            .action(actionName)
//                        if (keyModel != null) {
//                            builderRefresh.data(keyModel.map)
//                        }
//                        val requestBodyRefresh =
//                            builderRefresh.requestId(refreshRequestId)
//                                .token(getCacheToken())
//                                .packageName(getPackageName()).build()
//                        ILogUtils.e("newToken====" + getCacheToken())
//                        val refreshCall = Request.Builder()
//                            .url(connectionUrl)
//                            .post(
//                                requestBodyRefresh.toEncryptString(getCoreKey())
//                                    .toRequestBody("text/plain".toMediaTypeOrNull())
//                            )
//                            .build()
                        val refreshRequest =
                            OkHttpClientSingle.getOkhttpClient().newCall(makeRequest(actionName,refreshRequestId,keyModel))
                        refreshRequest.enqueue(object : Callback {
                            override fun onFailure(call: Call, e: IOException) {
                                onFailure(e.message.toString())
                            }

                            override fun onResponse(call: Call, response: Response) {
                                if (response.code == 200 && response.body != null) {
                                    val data = decodeResponseData(
                                        actionName,
                                        refreshRequestId,
                                        response
                                    )
                                    if (data.isSuccess) {
                                        onSuccess(data)
                                    } else {
                                        onFailure(httpData.message)
                                    }
                                } else {
                                    onFailure("")
                                }
                            }
                        })
                    } else {
                        onFailure("")
                    }
                } else {
                    onFailure("")
                }
            }
        })
}


object ApplicationContextObject {
    var application: Application? = null
}

// 需要进行分层拦截的接口
val interceptorActions = mutableListOf<String>("")

/**
 * body加密直接在这里进行不需要走拦截器
 * 由于每次请求都是相同URL，只是用actionName用路由，所以不使用retrofit,直接使用okhttp
 */
@OptIn(ExperimentalCoroutinesApi::class)
//suspend fun <T> okhttpFlowRequest(
//    actionName: String,
//    clazz: Class<T>,
//    keyModel: KeyModel? = KeyModel.create(),
//    netFailed: (rxError: RxError) -> Unit = {}
//): Flow<T> = callbackFlow {
//    ILogUtils.e("请求开始===$actionName===requestModel===${keyModel?.map.toString()}")
////    NetBiEventModel.track(actionName = actionName, requestModel = keyModel, start = true)
//    RxRequest
//        .with(ApplicationContextObject.application)
//        .setAction(actionName)
//        .setKeyModel(keyModel)
//        .request(object : RxModelCallback<T> {
//            override fun success(p0: T) {
//                ILogUtils.e("返回数据为： requestAction===$actionName===success===$p0")
//                if (p0 == null) {
//                    cancel("网络请求失败： requestAction===$actionName===failed===${p0}")
//                }
//                if (isActive) {
//                    trySend(p0)
//                    close()
//                }
//            }
//
//            override fun failed(p0: RxError?) {
//                ILogUtils.e("网络请求失败： requestAction===$actionName===failed===${p0?.error}")
//                p0?.let {
//                    netFailed(it)
//                }
//                close()
//            }
//        }, clazz)
//    awaitClose {
//        ILogUtils.d("close connection $actionName")
//        if (isActive) {
//            cancel()
//        }
////        close()
//    }
//}
//
//@OptIn(ExperimentalCoroutinesApi::class)
//suspend fun okhttpFlowRequestString(
//    actionName: String,
//    keyModel: KeyModel? = KeyModel.create(),
//    netFailed: (p0: String?, p1: String?) -> Unit = { p0, p1 -> }
//): Flow<String> = callbackFlow {
//    ILogUtils.e("请求开始===$actionName===requestModel===")
//    RxRequest
//        .with(ApplicationContextObject.application)
//        .setAction(actionName)
//        .setKeyModel(keyModel)
//        .request(object : RxCallback {
//            override fun success(p0: String) {
//                ILogUtils.e("返回数据为： requestAction===$actionName===success===$p0")
//                if (p0.isEmptyOrNull()) {
//                    cancel("网络请求失败： requestAction===$actionName===failed===${p0}")
//                }
//                if (isActive) {
//                    trySend(p0)
//                    close()
//                }
//            }
//
//            override fun failed(p0: String?, p1: String?) {
//                ILogUtils.e("网络请求失败： requestAction===$actionName===failed===${p0}===${p1}")
//                netFailed(p0, p1)
//                close()
//            }
//        })
//    awaitClose {
//        ILogUtils.d("close connection $actionName")
//        if (isActive) {
//            cancel()
//        }
////        close()
//    }
//}
//
//@OptIn(ExperimentalCoroutinesApi::class)
//suspend fun <T> okhttpFlowRequestList(
//    actionName: String,
//    clazz: Class<T>,
//    keyModel: KeyModel? = KeyModel.create(),
//    netFailed: (rxError: RxError) -> Unit = {},
//): Flow<List<T>> = callbackFlow {
//    ILogUtils.e("请求开始===$actionName===requestModel===")
//    RxRequest
//        .with(ApplicationContextObject.application)
//        .setAction(actionName)
//        .setKeyModel(keyModel)
//        .request(object : RxListCallback<List<T>> {
//            override fun success(p0: List<T>?) {
//                ILogUtils.e("返回数据为： requestAction===$actionName===success===$p0")
//                if (p0 == null) {
//                    cancel("网络请求失败：  requestAction===$actionName===failed===${p0}")
//                }
//                if (isActive && p0 != null) {
//                    trySend(p0)
//                    close()
//                }
//            }
//
//            override fun failed(p0: RxError?) {
//                ILogUtils.e("网络请求失败： requestAction===$actionName===failed===${p0?.error}")
//                p0?.let {
//                    netFailed(it)
//                }
//                close()
//
//            }
//        }, clazz)
//    awaitClose {
//        ILogUtils.d("close connection $actionName")
//        if (isActive) {
//            cancel()
//        }
////        close()
//    }
//}


fun <T> Flow<T>.catchError(bloc: Throwable.() -> Unit) = catch { cause ->
    ILogUtils.e("connector===error==" + cause.message.toString())
    bloc(cause)
}

suspend fun <T> Flow<T>.next(bloc: suspend T.() -> Unit): Unit = catch { }.collect { bloc(it) }

//suspend fun <T>Flow<String>.tojsonObj(bloc: suspend T.() -> Unit)= collect { bloc(GsonSingle.getInstance().fromJson(it,T::class.java)) }
//
//suspend fun <T>Flow<T>.tojsonObj(bloc: suspend T.() -> Unit)= collect { bloc(it) }