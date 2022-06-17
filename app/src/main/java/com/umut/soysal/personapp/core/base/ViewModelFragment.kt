package com.umut.soysal.personapp.core.base

import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.umut.soysal.personapp.core.constant.GlobalConstant
import com.umut.soysal.personapp.uicomponent.LoadingDialog

abstract class ViewModelFragment<T : BaseViewModel> : BaseFragment() {

    abstract val viewModel: T
    private var loadingCount: Int = 0
    private val loadingFragment by lazy { LoadingDialog() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observeLoading()
        observeError()
    }

    open fun observeLoading() {
        viewModel.loading.observe(this, Observer {
            onLoading(it)
        })
    }

    open fun observeError() {
        viewModel.error.observe(this, Observer { message ->
            onError(message)
        })
    }

    @Synchronized
    private fun onLoading(show: Boolean) {
        if (show) {
            if (!loadingFragment.isVisible) {
                loadingFragment.showView(requireActivity().fragmentManager, GlobalConstant.TAG_LOADING)
            }
            loadingCount++
        } else {
            loadingCount--
            if (loadingCount < 1) {
                loadingFragment?.dismissAllowingStateLoss()
                loadingCount = 0
            }
        }
    }

    fun onError(message: String?) {
        AlertDialog.Builder(requireActivity())
            .setTitle("Error")
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()
    }

    override fun onDestroy() {
        super.onDestroy()
        loadingFragment.dismissAllowingStateLoss()
    }

    override fun onPause() {
        super.onPause()
        loadingFragment.dismissAllowingStateLoss()
    }
}