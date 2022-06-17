package com.umut.soysal.personapp.uicomponent

import android.app.DialogFragment
import android.app.FragmentManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.umut.soysal.personapp.R
import com.umut.soysal.personapp.core.constant.GlobalConstant
import java.lang.IllegalStateException

class LoadingDialog: DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        isCancelable = false
        return inflater.inflate(R.layout.layout_loading_view, container, false)
    }

    override fun show(manager: FragmentManager, tag: String?) {
        tag?.let {
            if (manager.findFragmentByTag(it) != null) {
                return
            }
        }
        try {
            manager.executePendingTransactions()
            val ft = manager.beginTransaction()
            ft.add(this, GlobalConstant.TAG_LOADING)
            ft.commitAllowingStateLoss()
        } catch (ignored: IllegalStateException) {
        }
    }

    fun showView(manager: FragmentManager, tag: String?) {
        show(manager, tag)
    }
}