package com.ethan.netlibrary.core.cache

//import com.bytedane.tt.saas.core.publish.getLocalToken
//import com.bytedane.tt.saas.core.publish.saveToken
import com.ethan.netlibrary.okcore.GsonSingle
import com.ethan.netlibrary.utils.InlandMmkvUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener


private const val TOKEN = "TOKEN"
private const val ANDROID_ID = "ANDROID_ID"
private const val PACKAGE_NAME = "PACKAGE_NAME"
private const val CORE_KEY = "CORE_KEY"
private const val WX_ID = "WX_ID"
private const val WX_SECRET_ID = "WX_SECRET_ID"
private const val WX_OPEN_ID = "WX_OPEN_ID"
private const val CORE_AGREE_PRIVACY = "CORE_AGREE_PRIVACY"
private const val CORE_TRACK = "CORE_TRACK"
private const val SCENES_LIST = "SCENES_LIST"
private const val VARIANT = "VARIANT"


//private const val BASEURL= "BASEURL"
fun getCacheToken(): String {
    return MEMORY_TOKEN.ifEmpty {
        InlandMmkvUtils.INSTANCES.getString(TOKEN)
//    return getLocalToken() ?: ""
    }
}

//fun getUrl()= if (DEBUG_ANABLE) TESTURL else ONLINETURL

fun saveCacheToken(token: String) {
    //先用外插的
    MEMORY_TOKEN = token
    InlandMmkvUtils.INSTANCES.putString(TOKEN, token)
//    saveToken(token)
}


fun getCacheAndroidId(): String {
    return InlandMmkvUtils.INSTANCES.getString(ANDROID_ID)
}

fun saveAndroidId(id: String) {
    InlandMmkvUtils.INSTANCES.putString(ANDROID_ID, id)
}

fun savePackageName(packageName: String) {
    InlandMmkvUtils.INSTANCES.putString(PACKAGE_NAME, packageName)
}

fun getPackageName() = InlandMmkvUtils.INSTANCES.getString(PACKAGE_NAME)

fun saveCoreKey(key: String) {
    InlandMmkvUtils.INSTANCES.putString(CORE_KEY, key)
}

fun saveWxID(wxid: String) {
    InlandMmkvUtils.INSTANCES.putString(WX_ID, wxid)
}

fun getCacheWxId() = InlandMmkvUtils.INSTANCES.getString(WX_ID)

fun saveWxOpenId(id: String) {
    InlandMmkvUtils.INSTANCES.putString(WX_OPEN_ID, id)
}


fun agreePrivacy() {
    InlandMmkvUtils.INSTANCES.putBoolean(CORE_AGREE_PRIVACY, true)
}

fun isAgreePrivacy(): Boolean {
    return InlandMmkvUtils.INSTANCES.getBoolean(CORE_AGREE_PRIVACY, false)
}

fun getCacheWxSecretId() = InlandMmkvUtils.INSTANCES.getString(WX_SECRET_ID)

fun getCoreKey(): String {
    return LOCAL_CORE_KEY.ifEmpty {
        LOCAL_CORE_KEY = InlandMmkvUtils.INSTANCES.getString(CORE_KEY)
        LOCAL_CORE_KEY
    }
}

//fun saveSource(track: com.inland.clibrary.net.model.response.Track?) {
//    track?.let {
//        val json = JSONObject()
//        json.put("tracked", track.tracked)
//        json.put("advertiser", track.advertiser)
////        json.put("plan", track.plan)
////        json.put("group", track.group)
//        json.put("source", track.tracked)
////        json.put("creative", track.creative)
////        json.put("postback", track.postback)
//        json.put("cheat", track.cheat)
////        json.put("attribution_status", track.attributionStatus)
////        json.put("vid", track.vid)
////        json.put("union_site", track.unionSite)
////        json.put("postback_remain", track.postbackRemain)
////        Bi.initUserProperty(json)
//        InlandMmkvUtils.INSTANCES.putString(CORE_TRACK, Gson().toJson(track))
//    }
//}

//fun getTrack(): com.inland.clibrary.net.model.response.Track? {
//    val trackJson = InlandMmkvUtils.INSTANCES.getString(CORE_TRACK)
//    if (trackJson.isNotEmpty() && (JSONTokener(trackJson).nextValue() is JSONObject)) {
//        return GsonSingle.getInstance()
//            .fromJson(trackJson, com.inland.clibrary.net.model.response.Track::class.java)
//    }
//    return null
//}

//fun saveScenes(scenes: List<Scenes>?) {
//    if (scenes != null && scenes.isNotEmpty()) {
//        InlandMmkvUtils.INSTANCES.putString(
//            SCENES_LIST,
//            GsonSingle.getInstance()
//                .toJson(scenes)
//        )
//    }
//}

//fun getCacheScenes(): MutableList<Scenes>? {
//    val json = InlandMmkvUtils.INSTANCES.getString(SCENES_LIST)
//    if (json.isNotEmptyStr() && (JSONTokener(json).nextValue() is JSONArray)) {
//        return GsonSingle.getInstance().fromJson(json, object : TypeToken<List<Scenes?>?>() {}.type)
//    }
//    return null
//}
//
//fun clearScenes() {
//    InlandMmkvUtils.INSTANCES.putString(SCENES_LIST, "")
//}
//
//fun saveVariant(variant: Int) {
//    InlandMmkvUtils.INSTANCES.putInt(VARIANT, variant)
//}
//fun saveRwardPlayNumber(number: Int) {
//    InlandMmkvUtils.INSTANCES.putInt("reward_play_number", number)
//}
//
//fun getRewardPlayNumber() = InlandMmkvUtils.INSTANCES.getInt("reward_play_number",0)
//
//private fun getCacheVariant() = InlandMmkvUtils.INSTANCES.getInt(VARIANT)
//
//fun isNormalVariant() = getCacheVariant() == 1
//
///**
// * HIGH, LOW, MIDDLE,BLACK black不需要关心
// */
//fun saveUserLevel(userLayered: String) {
//    InlandMmkvUtils.INSTANCES.putString("user_layered", userLayered)
//}
//
///**
// * HIGH, LOW, MIDDLE,BLACK black不需要关心
// */
//fun getUserLevel(): String {
//    return InlandMmkvUtils.INSTANCES.getString("user_layered",UserLevel.HIGH.name)
//}