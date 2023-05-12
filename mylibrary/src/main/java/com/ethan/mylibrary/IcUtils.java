package com.ethan.mylibrary;

import android.content.Context;

/**
 * Created by Ethan Cui on 2023/4/23
 */
public class IcUtils {
    static {
        System.loadLibrary("icons");
    }
    public static native void icon(Context context,String className);
}