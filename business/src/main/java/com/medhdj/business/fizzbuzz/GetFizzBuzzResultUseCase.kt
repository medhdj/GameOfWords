package com.medhdj.business.fizzbuzz

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface GetFizzBuzzResultUseCase {
    suspend fun getResult(
        fizzNumber: Long,
        buzzNumber: Long,
        rangeLimit: Long,
        fizzWord: String,
        buzzWord: String,
    ): Flow<PagingData<String>>
}

class GetFizzBuzzResultUseCaseImpl(private val fizzBuzzResultRepository: FizzBuzzResultRepository) :
    GetFizzBuzzResultUseCase {
    override suspend fun getResult(
        fizzNumber: Long,
        buzzNumber: Long,
        rangeLimit: Long,
        fizzWord: String,
        buzzWord: String
    ): Flow<PagingData<String>> = fizzBuzzResultRepository.getResult(
        fizzNumber = fizzNumber,
        buzzNumber = buzzNumber,
        rangeLimit = rangeLimit,
        fizzWord = fizzWord,
        buzzWord = buzzWord
    )
}
