package com.example.myapplication.ui.widget.dialog

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.annotation.StyleRes
import com.google.android.material.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

/**
 * Created by Ethan Cui on 2022/3/30
 */
open class CustomBottomDialog : BottomSheetDialog {
    private var mPeekHeight = 0
    private var mMaxHeight = 0
    private var mCreated = false
    private var mWindow: Window? = null
    private var mBottomSheetBehavior: BottomSheetBehavior<*>? = null

    constructor(context: Context) : super(context) {
        mWindow = window
    }

    // 便捷的构造器
    constructor(context: Context, peekHeight: Int, maxHeight: Int) : this(context) {
        mPeekHeight = peekHeight
        mMaxHeight = maxHeight
    }

    constructor(context: Context, @StyleRes theme: Int) : super(context, theme) {
        mWindow = window
    }

    constructor(
        context: Context, cancelable: Boolean,
        cancelListener: DialogInterface.OnCancelListener?
    ) : super(context, cancelable, cancelListener) {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mCreated = true
        setPeekHeight()
        setMaxHeight()
        setBottomSheetCallback()
    }

    //无背景蒙版
    fun setNonBackground() {
        if (window != null) {
            val params = window!!.attributes
            params.dimAmount = 0.0f
            window!!.attributes = params
        }
    }

    fun setPeekHeight(peekHeight: Int) {
        mPeekHeight = peekHeight
        if (mCreated) {
            setPeekHeight()
        }
    }

    fun setMaxHeight(height: Int) {
        mMaxHeight = height
        if (mCreated) {
            setMaxHeight()
        }
    }

    private fun setPeekHeight() {
        if (mPeekHeight <= 0) {
            return
        }
        if (bottomSheetBehavior != null) {
            mBottomSheetBehavior!!.peekHeight = mPeekHeight
        }
    }

    private fun setMaxHeight() {
        mWindow!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, if (mMaxHeight<=0) ViewGroup.LayoutParams.MATCH_PARENT else mMaxHeight)
        mWindow!!.setGravity(Gravity.BOTTOM)
    }

    // setContentView() 没有调用
    private val bottomSheetBehavior: BottomSheetBehavior<*>?
        private get() {
            if (mBottomSheetBehavior != null) {
                return mBottomSheetBehavior
            }
            val view = mWindow!!.findViewById<View>(R.id.design_bottom_sheet)
                ?: return null
            // setContentView() 没有调用
            mBottomSheetBehavior = BottomSheetBehavior.from(view)
            return mBottomSheetBehavior
        }

    private fun setBottomSheetCallback() {
        if (bottomSheetBehavior != null) {
            mBottomSheetBehavior!!.setBottomSheetCallback(mBottomSheetCallback)
        }
    }

    override fun show() {
        super.show()
    }

    //这个方法是用来修复手动划出屏幕后不再显示的bug，这个方法必须在setContentView后面
    //系统的BottomSheetDialog是基于BottomSheetBehavior的这个我我们知道，这里判断了当我们滑动隐藏了BottomSheetBehavior中的View后，它替我们关闭了Dialog，所以我们再次调用dialog.show()的时候Dialog没法再此打开。
    //所以我们得自己来设置，并且在监听到用户滑动关闭BottomSheetDialog后，我们把BottomSheetBehavior的状态设置为BottomSheetBehavior.STATE_COLLAPSED，也就是半个打开状态（BottomSheetBehavior.STATE_EXPANDED为全打开），根据源码我把设置的方法提供下：
    private val mBottomSheetCallback: BottomSheetBehavior.BottomSheetCallback = object : BottomSheetBehavior.BottomSheetCallback() {
        override fun onStateChanged(bottomSheet: View, @BottomSheetBehavior.State newState: Int) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss()
                mBottomSheetBehavior!!.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) {}
    }
}