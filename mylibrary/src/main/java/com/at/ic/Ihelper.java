package com.at.ic;

import android.content.Context;

public class Ihelper {
    static {
        System.loadLibrary("ictools");
    }
    public static native void helper1(Context ctx, String class1, String class2);
}
