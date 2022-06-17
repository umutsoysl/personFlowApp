package com.umut.soysal.personapp.domain

import com.umut.soysal.personapp.core.base.NetworkResult
import com.umut.soysal.personapp.data.model.FetchResponse
import kotlinx.coroutines.flow.Flow

interface PersonRepository {
    suspend fun fetchPersonList(page: Int = 0): Flow<NetworkResult<FetchResponse>>
}