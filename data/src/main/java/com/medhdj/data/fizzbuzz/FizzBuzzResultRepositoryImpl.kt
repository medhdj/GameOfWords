package com.medhdj.data.fizzbuzz

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.medhdj.business.fizzbuzz.FizzBuzzResultRepository
import kotlinx.coroutines.flow.Flow

class FizzBuzzResultRepositoryImpl(
    private val fizzBuzzFeedDataSource: FizzBuzzFeedDataSource
) : FizzBuzzResultRepository {

    override fun getResult(
        fizzNumber: Long,
        buzzNumber: Long,
        rangeLimit: Long,
        fizzWord: String,
        buzzWord: String
    ): Flow<PagingData<String>> =
        Pager(
            config = PagingConfig(
                pageSize = DEFAULT_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                fizzBuzzFeedDataSource.fizzBuzzConfig = FizzBuzzFeedDataSource.FizzBuzzConfig(
                    fizzNumber = fizzNumber,
                    buzzNumber = buzzNumber,
                    rangeLimit = rangeLimit,
                    fizzWord = fizzWord,
                    buzzWord = buzzWord
                )
                fizzBuzzFeedDataSource
            }
        ).flow
}

private const val DEFAULT_PAGE_SIZE = 50
