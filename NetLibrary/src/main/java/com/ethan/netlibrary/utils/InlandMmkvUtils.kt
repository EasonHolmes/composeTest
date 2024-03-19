package com.ethan.netlibrary.utils

import com.tencent.mmkv.MMKV

class InlandMmkvUtils private constructor(private val mmkv: MMKV?) {


    fun getString(key: String, defaultValue: String = ""): String {
        val value = mmkv?.decodeString(key, defaultValue)
        return if (value.isNullOrEmpty()) defaultValue else value
    }

    fun getInt(key: String, defaultValue: Int = 0): Int {
        val value = mmkv?.decodeInt(key, defaultValue)
        value?.let {
            return value
        }
        return defaultValue
    }

    fun getLong(key: String, defaultValue: Long = 0L): Long {
        val value = mmkv?.decodeLong(key, defaultValue)
        value?.let {
            return value
        }
        return defaultValue
    }

    fun getFloat(key: String, defaultValue: Float = 0f): Float {
        val value = mmkv?.decodeFloat(key, defaultValue)
        value?.let {
            return value
        }
        return defaultValue
    }
    fun getDouble(key: String, defaultValue: Double = 0.0): Double {
        val value = mmkv?.decodeDouble(key, defaultValue)
        value?.let {
            return value
        }
        return defaultValue
    }

    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean {
        val value = mmkv?.decodeBool(key, defaultValue)
        value?.let {
            return value
        }
        return defaultValue
    }

    fun putString(key: String, value: String) {
        mmkv?.encode(key, value)
    }

    fun putInt(key: String, value: Int) {
        mmkv?.encode(key, value)
    }


    fun putLong(key: String, value: Long) {
        mmkv?.encode(key, value)
    }

    fun putFloat(key: String, value: Float) {
        mmkv?.encode(key, value)
    }

    fun putDouble(key:String,value:Double){
        mmkv?.encode(key,value)
    }

    fun putBoolean(key: String, value: Boolean) {
        mmkv?.encode(key, value)
    }

    fun clear() {
        mmkv?.clearAll()
    }

    companion object {
        val INSTANCES: InlandMmkvUtils by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            InlandMmkvUtils(MMKV.mmkvWithID("MMKV_ID"))
        }
    }
}