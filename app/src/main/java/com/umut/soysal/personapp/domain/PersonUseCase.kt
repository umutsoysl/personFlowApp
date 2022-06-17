package com.umut.soysal.personapp.domain

import com.umut.soysal.personapp.core.base.NetworkResult
import com.umut.soysal.personapp.data.model.FetchResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class PersonUseCase @Inject constructor(
    private val repository: PersonRepository
) {
    suspend operator fun invoke(
        page: Int = 0,
        dispatcher: CoroutineDispatcher = Dispatchers.IO
    ): Flow<NetworkResult<FetchResponse>> {
        return repository.fetchPersonList(page = page)
            .flowOn(dispatcher)
    }
}