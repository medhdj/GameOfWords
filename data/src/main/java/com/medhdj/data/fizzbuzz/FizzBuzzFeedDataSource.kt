package com.medhdj.data.fizzbuzz

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FizzBuzzFeedDataSource : PagingSource<Long, String>() {
    lateinit var fizzBuzzConfig: FizzBuzzConfig

    override suspend fun load(params: LoadParams<Long>): LoadResult<Long, String> {
        return withContext(Dispatchers.Default) {
            try {
                val rangeStart = params.key ?: DEFAULT_RANGE_START
                val rangeEnd = rangeStart + params.loadSize

                val result = generateFizzBuzzData(
                    from = rangeStart,
                    to = rangeEnd
                )

                val (prevKey, nextKey) = calculateKeys(
                    rangeStart,
                    rangeEnd,
                    params.loadSize,
                    result
                )

                LoadResult.Page(
                    data = result,
                    prevKey = prevKey,
                    nextKey = nextKey
                )
            } catch (exception: Exception) {
                // for any exception non handled, we inform the other layers of that error
                LoadResult.Error(exception)
            }
        }
    }

    private fun calculateKeys(
        rangeStart: Long,
        rangeEnd: Long,
        loadSize: Int,
        result: List<String>
    ): Pair<Long?, Long?> {
        val prevKey = (rangeStart - loadSize - 1).let {
            if (it <= DEFAULT_RANGE_START) null
            else {
                it
            }
        }

        val nextKey =
            if (result.isNotEmpty() && rangeEnd < fizzBuzzConfig.rangeLimit) rangeEnd + 1 else null

        return Pair(prevKey, nextKey)
    }

    private fun generateFizzBuzzData(from: Long, to: Long): List<String> =
        kotlin.runCatching {
            check(from < to)
            check(from < Long.MAX_VALUE && to <= Long.MAX_VALUE)

            val result = mutableListOf<String>()
            val loopLimit = minOf(to, fizzBuzzConfig.rangeLimit)
            for (i in from..loopLimit) {
                result += with(fizzBuzzConfig) {
                    when {
                        i % fizzNumber == 0L && i % buzzNumber == 0L -> {
                            "$fizzWord$buzzWord"
                        }
                        i % fizzNumber == 0L -> {
                            fizzWord
                        }
                        i % buzzNumber == 0L -> {
                            buzzWord
                        }
                        else -> "$i"
                    }
                }
            }
            result
        }.getOrDefault(emptyList())


    override fun getRefreshKey(state: PagingState<Long, String>): Long? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
                ?: state.closestPageToPosition(anchorPosition)?.nextKey
        }
    }

    data class FizzBuzzConfig(
        val fizzNumber: Long,
        val buzzNumber: Long,
        val rangeLimit: Long,
        val fizzWord: String,
        val buzzWord: String
    )
}

private const val DEFAULT_RANGE_START = 1L
