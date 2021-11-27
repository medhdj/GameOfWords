package com.medhdj.business.fizzbuzz

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface FizzBuzzResultRepository {
    fun getResult(
        fizzNumber: Long,
        buzzNumber: Long,
        rangeLimit: Long,
        fizzWord: String,
        buzzWord: String,
    ): Flow<PagingData<String>>
}