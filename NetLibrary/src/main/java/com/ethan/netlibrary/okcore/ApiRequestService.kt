package com.ethan.netlibrary.okcore

/**
 * Created by Ethan Cui on 2022/3/27
 * 所有接口统一封装类，不要单独使用某个类
 * 都在这里调用，后期方便调整，如果后期后台能解决token的事情，actionName使用url的方式则使用retrofit+okhttp,此类就不用了
 */
class ApiRequestService private constructor(){
    companion object {
        val INSTANCES: ApiRequestService by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            ApiRequestService()
        }
    }
//    val userConnector by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { UserConnector() }
//    val signConnector by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { SignConnector() }
//    val goldsConnector by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { GoldsConnector() }
//    val bubbleConnector by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { BubbleConnector() }
//    val cashConnector by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { CashConnector() }
//    val rewardedConnector by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { RewardedConnector() }
//    val evenlopeConnector by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { EvenlopeConnector() }
}