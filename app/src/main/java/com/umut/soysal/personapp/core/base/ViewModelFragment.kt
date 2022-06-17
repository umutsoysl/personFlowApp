package com.umut.soysal.personapp.core.base

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer

abstract class ViewModelFragment<T : BaseViewModel> : BaseFragment() {

    abstract val viewModel: T
    private var loadingDialog: Dialog? = null
    private var loadingCount: Int = 0

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
            if (loadingDialog == null)
                //loadingDialog = CCSIPopup.showLoadingPopup(context)
            loadingCount++
        } else {
            loadingCount--
            if (loadingCount < 1) {
                loadingDialog?.dismiss()
                loadingDialog = null
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
        loadingDialog?.dismiss()
    }

    override fun onPause() {
        super.onPause()
        loadingDialog?.dismiss()
    }
}