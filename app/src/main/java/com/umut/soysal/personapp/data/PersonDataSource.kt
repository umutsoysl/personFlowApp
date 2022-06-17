package com.umut.soysal.personapp.data

import DataSource
import com.umut.soysal.personapp.core.base.NetworkResult
import com.umut.soysal.personapp.data.model.FetchCompletionHandler
import com.umut.soysal.personapp.data.model.FetchError
import com.umut.soysal.personapp.data.model.FetchResponse
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject

class PersonDataSource @Inject constructor() {

    suspend fun fetchPersonList(page: Int = 0) = flow<NetworkResult<FetchResponse>> {
        if (page <= 1) {
            emit(NetworkResult.Loading())
        }
        emit(
            suspendCancellableCoroutine {
                DataSource().fetch(
                    next = page.toString(),
                    completionHandler = object : FetchCompletionHandler {
                        override fun invoke(response: FetchResponse?, error: FetchError?) {
                            error?.let { response ->
                                if (response.errorDescription.isNotEmpty()) {
                                    it.resume(NetworkResult.Error(response.errorDescription, null), null)
                                }
                            }

                            response?.let { response ->
                                it.resume(NetworkResult.Success(response), null)
                            }
                        }
                    }
                )
            }
        )
    }
}