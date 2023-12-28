package com.example.myapplication.ui.widget.dialog

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


/**
 *
 * 更加强壮的 BottomSheetDialog
 *  * 增加了设置显示高度跟最大高度的方法
 *  * 修复了通过手势关闭后无法再显示的问题
 *
 */
abstract class ComposeBottomDialog<VM : ViewModel> : BottomSheetDialogFragment() {
    protected val mViewModel: VM by lazy { ViewModelProvider(this)[getViewModel()] }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return ComposeView(requireContext()).apply {
            setContent {
                MaterialTheme() {
                    ContentView(false)
                    roundBackground(LocalView.current)
                }
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return CustomBottomDialog(requireContext())
    }
    var isHideable = false
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dialog = dialog as CustomBottomDialog
        //默认展开
        dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
//        dialog.setMaxHeight(1600)
//        dialog.setNonBackground()
        dialog.behavior.isHideable = isHideable//false禁止向下拖动
        dialog.window?.setDimAmount(0.4f)

    }
    fun setIshideable(bool:Boolean){
        this.isHideable = bool
    }


    private fun roundBackground(rootView: View) {
        rootView.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                if (Build.VERSION.SDK_INT < 16) {
                    rootView.viewTreeObserver.removeGlobalOnLayoutListener(this)
                } else {
                    rootView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                }
                val dialog = dialog as BottomSheetDialog?
                val bottomSheet =
                    dialog!!.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout?
                bottomSheet!!.setBackgroundResource(R.drawable.corners_top_white)
            }
        })
    }

    override fun show(manager: FragmentManager, tag: String?) {
        if (!manager.isDestroyed && !manager.isStateSaved) {
            try {
                //在每个add事务前增加一个remove事务，防止连续的add
                manager.beginTransaction().remove(this).commit();
                super.show(manager, tag);
            } catch (e: Exception) {
                //同一实例使用不同的tag会异常,这里捕获一下
                e.printStackTrace();
            }
        }
    }

    override fun dismiss() {
        dismissAllowingStateLoss()
    }

    override fun show(transaction: FragmentTransaction, tag: String?): Int {
        return super.show(transaction, tag)
    }

    protected abstract fun getViewModel(): Class<VM>

    @Composable
    protected abstract fun ContentView(preview:Boolean)
}