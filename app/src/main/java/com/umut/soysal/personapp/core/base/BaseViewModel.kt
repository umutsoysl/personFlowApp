package com.umut.soysal.personapp.core.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.umut.soysal.personapp.core.utils.SingleLiveData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect


abstract class BaseViewModel: ViewModel() {
    private val _loading = SingleLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = SingleLiveData<String>()
    val error: LiveData<String> = _error

    fun showLoading() {
        _loading.value = true
    }

    fun dismissLoading(call: () -> Unit) {
        _loading.value = false
        call.invoke()
    }

    fun handleError(error: String) {
        _error.value = error
    }

    suspend inline fun <T> Flow<NetworkResult<T>>.collectNetworkResult(
        crossinline onSuccess: (T?) -> Unit,
        noinline onError: ((String) -> Unit)? = null,
        crossinline onLoading: () -> Unit = ::showLoading
    ) {
        collect { response ->
            when (response) {
                is NetworkResult.Success -> dismissLoading { onSuccess.invoke(response.data) }
                is NetworkResult.Error -> dismissLoading { onError?.invoke(response.error) }
                is NetworkResult.Loading -> onLoading()
            }
        }
    }
}