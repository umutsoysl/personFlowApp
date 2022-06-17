package com.umut.soysal.personapp.feature

import androidx.lifecycle.viewModelScope
import com.umut.soysal.personapp.core.base.BaseViewModel
import com.umut.soysal.personapp.data.model.FetchResponse
import com.umut.soysal.personapp.data.model.Person
import com.umut.soysal.personapp.domain.PersonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val personUseCase: PersonUseCase
): BaseViewModel() {

    private val _personList = MutableStateFlow<List<Person>>(listOf())
    val personList: StateFlow<List<Person>> = _personList.asStateFlow()

    private val page: Int = 1

    fun fetchPersonList() {
        viewModelScope.launch {
            personUseCase.invoke(page).collectNetworkResult(
                onSuccess = ::handleFetchPersonResponse,
                onError = ::handleError
            )
        }
    }

    private fun handleFetchPersonResponse(response: FetchResponse?) {
        response?.let { result->
            _personList.value += result.people
        }
    }
}