package com.ethan.netlibrary.core

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Ethan Cui on 2022/3/22
 */
class InlandConfiguration private constructor(
    val versionName: String,
    val debug: Boolean,
    val logEnable: Boolean,
    val channel: String,
    val key: String,
    val packageName: String,
    val wxId: String,
    val wxSecretID: String

) {
    companion object {
        inline fun build(block: Builder.() -> Unit) = Builder().apply(block).build()
    }

    class Builder() : Parcelable {
        //默认值
        var versionName: String = ""
        var debug: Boolean = false
        var logEnable: Boolean = false
        var channel: String = ""
        var pKey: String = ""
        var packageName: String = ""
        var wxId: String = ""
        var wxSecretID: String = ""
        fun build(): InlandConfiguration {
            return InlandConfiguration(versionName, debug, logEnable, channel, pKey, packageName,wxId,wxSecretID)
        }

        constructor(parcel: Parcel) : this() {
            versionName = parcel.readString().toString()
            debug = parcel.readByte() != 0.toByte()
            logEnable = parcel.readByte() != 0.toByte()
            channel = parcel.readString().toString()
            pKey = parcel.readString().toString()
            packageName = parcel.readString().toString()
            wxId = parcel.readString().toString()
            wxSecretID = parcel.readString().toString()

        }


        override fun describeContents(): Int {
            return 0
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(channel)
            parcel.writeByte(if (logEnable) 1 else 0)
            parcel.writeByte(if (debug) 1 else 0)
            parcel.writeString(versionName)
            parcel.writeString(pKey)
            parcel.writeString(packageName)
            parcel.writeString(wxId)
            parcel.writeString(wxSecretID)
        }

        companion object CREATOR : Parcelable.Creator<Builder> {
            override fun createFromParcel(parcel: Parcel): Builder {
                return Builder(parcel)
            }

            override fun newArray(size: Int): Array<Builder?> {
                return arrayOfNulls(size)
            }
        }

    }
}