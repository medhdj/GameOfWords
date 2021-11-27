package com.medhdj.gow.features.fizzbuzz

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.medhdj.business.fizzbuzz.GetFizzBuzzResultUseCase
import com.medhdj.core.extension.mapSuccess
import com.medhdj.core.functionnal.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
    private val getFizzBuzzResultUseCase: GetFizzBuzzResultUseCase,
    private val state: SavedStateHandle
) : ViewModel() {
    companion object {
        const val FIZZ_NUMBER_KEY = "fizzNumber"
        const val BUZZ_NUMBER_KEY = "buzzNumber"
        const val RANGELIMIT_KEY = "rangeLimit"
        const val FIZZ_WORD_KEY = "fizzWord"
        const val BUZZ_WORD_KEY = "buzzWord"
    }

    val fizzNumber by lazy {
        state.get<Long>(FIZZ_NUMBER_KEY) ?: UNKNOWN_NUMBER
    }
    val buzzNumber by lazy {
        state.get<Long>(BUZZ_NUMBER_KEY) ?: UNKNOWN_NUMBER
    }
    val rangeLimit by lazy {
        state.get<Long>(RANGELIMIT_KEY) ?: UNKNOWN_NUMBER
    }
    val fizzWord by lazy {
        state.get<String>(FIZZ_WORD_KEY) ?: UNKNOWN_WORD
    }
    val buzzWord by lazy {
        state.get<String>(BUZZ_WORD_KEY) ?: UNKNOWN_WORD
    }

    private val pagingDataFlow =
        MutableLiveData<Response<Throwable, Flow<PagingData<String>>>>()

    val pagingData = pagingDataFlow.mapSuccess()

    init {
        fetchGameResults()
    }

    private fun fetchGameResults() {
        viewModelScope.launch {
            try {
                val result = getFizzBuzzResultUseCase.getResult(
                    fizzNumber = fizzNumber,
                    buzzNumber = buzzNumber,
                    rangeLimit = rangeLimit,
                    fizzWord = fizzWord,
                    buzzWord = buzzWord
                ).cachedIn(viewModelScope)

                pagingDataFlow.value = Response.Success(result)
            } catch (ex: Exception) {
                pagingDataFlow.value = Response.Failure(ex)
            }
        }
    }
}

private const val UNKNOWN_NUMBER = -1L
private const val UNKNOWN_WORD = "Unknown"
