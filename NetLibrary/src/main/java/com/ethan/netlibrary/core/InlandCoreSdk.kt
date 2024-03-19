package com.ethan.netlibrary.core

import android.app.Application
import com.ethan.netlibrary.core.InlandConfiguration
import com.ethan.netlibrary.core.cache.DEBUG_ANABLE
import com.ethan.netlibrary.core.cache.agreePrivacy
import com.ethan.netlibrary.core.cache.saveCoreKey
import com.ethan.netlibrary.core.cache.savePackageName
import com.ethan.netlibrary.core.cache.saveWxID
import com.ethan.netlibrary.utils.ILogUtils

/**
 * Created by Ethan Cui on 2022/3/22
 */
object InlandCoreSdk {

    @Synchronized
    fun initSdk(context: Application, inlandConfiguration: InlandConfiguration) {
        saveWxID(inlandConfiguration.wxId)
        savePackageName(inlandConfiguration.packageName)
        saveCoreKey(inlandConfiguration.key)
        ILogUtils.setDbug(inlandConfiguration.logEnable)
        DEBUG_ANABLE = inlandConfiguration.debug

        //异步检测模拟器，方便后便上报数据
//        detectorEmulator(context)
    }

//    suspend fun loginService(
//        context: Application,
//        openId: String = "",
//        longType: String = "",
//        inlandConfiguration: InlandConfiguration,
//        apiRequestClient: ApiRequestService = ApiRequestService.INSTANCES,
//        successLogin: (token: String) -> Unit = {},
//        successConfig: () -> Unit = {},
//        fail: (error: String) -> Unit = {}
//    ) {
//        apiRequestClient.userConnector.loginAction(
//            context,
//            inlandConfiguration = inlandConfiguration,
//            openId = openId,
//            loginType = longType,
//            successLogin = {
//                successLogin(getCacheToken())
//            }, failLogin = {
//                fail(it)
//            },
//            successConfig = {
//                successConfig()
//            }, failConfig = {
//                fail(it)
////                fail("访问登录或配置失败")
////                ILogUtils.e("初始化失败---登录失败${it}")
//            })
//    }

    fun updateAgreePrivacyState() {
        agreePrivacy()
    }
}