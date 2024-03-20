package com.ethan.netlibrary.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ethan.netlibrary.okcore.GsonSingle
import com.google.gson.reflect.TypeToken
import com.ethan.netlibrary.okcore.HttpData
import okhttp3.ResponseBody
import java.nio.charset.Charset


/**
 * Created by Ethan Cui on 2022/3/23
 */
fun ResponseBody.decodeAESCompress(actionName: String, key: String): HttpData<String> {
    val source = this.source()
    // Buffer the entire body.
    source.request(java.lang.Long.MAX_VALUE)
    val buffer = source.buffer()

    var charset: Charset? = Charset.forName("UTF-8")
    val contentType = this.contentType()
    if (contentType != null) {
        charset = contentType.charset(Charset.forName("UTF-8"))
    }

    val json = if (this.contentLength() != 0L && charset != null) {
        val json = buffer.clone().readString(charset)
        EncryptUtils.decodeAESCompress(actionName, key, json)
    } else {
        GsonSingle.getInstance().toJson(HttpData<String>())
    }
    return if (json.isNullOrEmpty()) HttpData<String>() else GsonSingle.getInstance()
        .fromJson(json, object : TypeToken<HttpData<String?>?>() {}.type)

}
//fun ResponseBody.decodeBody(string: String):String{
//
//}


fun <T> MutableLiveData<T>.asLiveData(): LiveData<T> {
    return this
}

//
//inline fun Context.registerWx(needTipDialog: Boolean = true,lifecycleOwner: LifecycleOwner,crossinline callback: (boolean:Boolean) -> Unit) {
//    if (needTipDialog) {
//        showSimpleDialogMessage(
//            "请先进行微信登录",
//            yesContent = getString(R.string.dialog_wx_yes),
//            cancleContent = getString(R.string.dialog_wx_cancel),
//            yesClicklistener = { dialog, which ->
//                LiveDataBus.get().with(WX_LOGIN_KEY, Boolean::class.java)
//                    .observe(lifecycleOwner) {
//                        callback(it)
//                    }
//                val api = WXAPIFactory.createWXAPI(this@registerWx, getCacheWxId(), true)
//                api?.let {
//                    it.registerApp(getCacheWxId())
//                    if (!it.isWXAppInstalled) {
//                        this@registerWx.toastContent("没有安装微信")
//                    } else {
//                        // send oauth request
//                        val req: com.tencent.mm.opensdk.modelmsg.SendAuth.Req =
//                            com.tencent.mm.opensdk.modelmsg.SendAuth.Req()
//                        req.scope = WX_SCOPE
//                        req.state = WX_STATE
//                        api.sendReq(req)
//                    }
//                }
//            }
//        )
//    } else {
//        LiveDataBus.get().with(WX_LOGIN_KEY, Boolean::class.java)
//            .observe(lifecycleOwner) {
//                callback(it)
//            }
//        val api = WXAPIFactory.createWXAPI(this, getCacheWxId(), true)
//        api?.let {
//            it.registerApp(getCacheWxId())
//            if (!it.isWXAppInstalled) {
//                this.toastContent("没有安装微信")
//            } else {
//                // send oauth request
//                val req: com.tencent.mm.opensdk.modelmsg.SendAuth.Req =
//                    com.tencent.mm.opensdk.modelmsg.SendAuth.Req()
//                req.scope = WX_SCOPE
//                req.state = WX_STATE
//                api.sendReq(req)
//            }
//        }
//    }
//}
//
//fun Context.showSimpleDialogMessage(
//    message: String,
//    yesContent: String = getString(R.string.text_yes),
//    cancleContent: String = getString(R.string.text_cancel),
//    yesClicklistener: DialogInterface.OnClickListener? = null
//) {
//    val b = AlertDialog.Builder(this@showSimpleDialogMessage)
//    b.setMessage(message)
//    b.setCancelable(false)
//    if (yesClicklistener != null) {
//        b.setPositiveButton(yesContent, yesClicklistener)
//        b.setNegativeButton(cancleContent) { dialog, _ -> dialog.cancel() }
//    } else {
//        b.setPositiveButton(R.string.text_know) { dialog, _ -> dialog.dismiss() }
//    }
//    if (this is AppCompatActivity) {
//        if (!isFinishing)
//            b.show().setCanceledOnTouchOutside(false)
//    }
//    if (this is Fragment) {
//        if (!isResumed)
//            b.show().setCanceledOnTouchOutside(false)
//    }
//}
//
//fun moveAppTaskToBack(context: Context) {
//    val intent = Intent("android.intent.action.MAIN")
//    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//    intent.addCategory("android.intent.category.HOME")
//    context.startActivity(intent)
//}
//
///**
// * 获取设备 ID 方法
// * @param ctx 当前上下文
// * @param sID 可选渠道号
// * @param optMsg 可选消息（
// * 这个自定义消息是为了做设备数据和用户数据关联的，例如您这面可以将用户ID通过自定义消息上传，我们ftp数据会将这个数据返回给您，您这面就可以做设备和用户数据的关联了
// * 也可以什么都不传
// * 您这面在用户注册登录的时候获取我们的设备ID，将设备ID保存到您的数据库里面，这样也可以做到设备和用户数据关联）
// * @@param waitOpt 异步标识；1 为异步
// * @param Listener 实现了 Listener 接口的类对象
// * @return null
// * @exception Exception
// */
//inline fun Context.shuMengWangyiCheck(crossinline success: (shumengDid: String) -> Unit = {}) {
//    GeneralBiTractUtils.tractEventJson("shumeng_id", mutableMapOf("start" to "开始调用"))
//    Main.getQueryID(
//        this@shuMengWangyiCheck, getMetaValue(this@shuMengWangyiCheck, "CHANNEL"), "optMsg", 1
//    ) { did ->
//        GeneralBiTractUtils.tractEventJson("shumeng_id", mutableMapOf("id" to did))
//        saveShumengDid(did)
//        if (this is AppCompatActivity) {
//            lifeScopeOnCreate {
//                success(getShumengDid())
//            }
//        } else if (this is Fragment) {
//            lifeScopeOnCreate {
//                success(getShumengDid())
//            }
//        }
//
//        //网易
////        GeneralBiTractUtils.tractEventJson("wangyi_id", mutableMapOf("start" to "开始调用"))
////        WatchMan.setSeniorCollectStatus(true)
////        WatchMan.getToken { code, msg, Token ->
////            saveWangyiToken(Token)
////            WatchMan.setSeniorCollectStatus(false)
////            GeneralBiTractUtils.tractEventJson("wangyi_id", mutableMapOf("id" to Token))
////            if (this is AppCompatActivity) {
////                lifeScopeOnCreate {
////                    success(Pair(getShumengDid(), getWangyiToken()))
////                }
////            } else if (this is Fragment) {
////                lifeScopeOnCreate {
////                    success(Pair(getShumengDid(), getWangyiToken()))
////                }
////            }
////        }
//    }
//}
////解决requireContext为空报错
//fun Fragment.getSafeString(res:Int):String{
//    return if (context!=null){
//        context!!.getString(res)
//    }else{
//        ""
//    }
//}
