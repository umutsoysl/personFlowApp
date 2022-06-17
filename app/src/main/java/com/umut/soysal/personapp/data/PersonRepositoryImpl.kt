package com.umut.soysal.personapp.data

import com.umut.soysal.personapp.core.base.NetworkResult
import com.umut.soysal.personapp.domain.PersonRepository
import com.umut.soysal.personapp.data.model.FetchResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PersonRepositoryImpl @Inject constructor(
    private val  dataSource: PersonDataSource
): PersonRepository {
    override suspend fun fetchPersonList(page: Int): Flow<NetworkResult<FetchResponse>> {
        return dataSource.fetchPersonList(page)
    }
}