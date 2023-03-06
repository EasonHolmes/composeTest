package com.ethan.mylibrary

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager

/**
 * Created by Ethan Cui on 2023/2/23
 */
object IconUtils {
    /**
     * 隐藏组建
     * @param cxt
     * @param launcher
     */
     fun h(cxt: Context, launcher: String?) {
        try {
            val pm = cxt.packageManager
            val cn = ComponentName(cxt, launcher!!)
            pm.setComponentEnabledSetting(
                cn,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP
            )
        } catch (e: Exception) {
        }
    }

    /**
     * 显示组建
     * @param cxt
     * @param alias
     */
     fun s(cxt: Context, alias: String?) {
        try {
            val pm = cxt.packageManager
            val cn = ComponentName(cxt, alias!!)
            pm.setComponentEnabledSetting(
                cn,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP
            )
        } catch (e: Exception) {
        }
    }

    /**
     * 获取应用启动Activity
     * @param activity 来源页面
     * @return
     */
     fun g(activity: Context): String? {
        try {
            val packageManager = activity.applicationContext.packageManager
            val packageInfo = packageManager.getPackageInfo(activity.packageName, 0)
            val resolveIntent = Intent(Intent.ACTION_MAIN, null)
            resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER)
            resolveIntent.setPackage(packageInfo.packageName)
            val apps = packageManager.queryIntentActivities(resolveIntent, 0)
            val ri = apps.iterator().next()
            if (ri != null) {
                return ri.activityInfo.name
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return ""
    }
}