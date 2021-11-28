package com.medhdj.data.fizzbuzz

import androidx.paging.PagingSource
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.`should be`
import org.amshove.kluent.shouldBeEmpty
import org.junit.Test

class FizzBuzzFeedDataSourceTest {

    private val tested = FizzBuzzFeedDataSource(
        dispatchProvider = {
            TestCoroutineDispatcher()
        }
    ).apply {
        fizzBuzzConfig = FizzBuzzFeedDataSource.FizzBuzzConfig(
            fizzNumber = 3L,
            buzzNumber = 5L,
            rangeLimit = 100,
            fizzWord = "fizz",
            buzzWord = "buzz"
        )
    }

    @Test
    fun `test calculateKeys when we are at the start of range`() {
        // given
        val rangeStart = 1L
        val rangeEnd = 50L
        val loadSize = 50
        val fizzBuzzList = List(3) { "a string" }

        // when
        val result = tested.calculateKeys(
            rangeStart = rangeStart,
            rangeEnd = rangeEnd,
            loadSize = loadSize,
            fizzBuzzList = fizzBuzzList
        )

        // then
        result.first `should be` null
        result.second `should be` 51
    }

    @Test
    fun `test calculateKeys when we are at the end of range`() {
        // given
        val rangeStart = 52L
        val rangeEnd = 102L
        val loadSize = 50
        val fizzBuzzList = List(3) { "a string" }

        // when
        val result = tested.calculateKeys(
            rangeStart = rangeStart,
            rangeEnd = rangeEnd,
            loadSize = loadSize,
            fizzBuzzList = fizzBuzzList
        )

        // then
        result.first `should be` 1
        result.second `should be` null
    }

    @Test
    fun `test generateFizzBuzzData behaviour`() {
        // given
        // different inputs

        // when
        val result1 = tested.generateFizzBuzzData(1, 51)
        val result2 = tested.generateFizzBuzzData(160, 51)
        val result3 = tested.generateFizzBuzzData(52, 102)
        val result4 = tested.generateFizzBuzzData(Long.MAX_VALUE, Long.MAX_VALUE)
        val result5 = tested.generateFizzBuzzData(Long.MAX_VALUE - 100, Long.MAX_VALUE)

        // then
        result1.size `should be` 51
        result2.shouldBeEmpty()
        result3.size `should be` 49
        result4.shouldBeEmpty()
        result5.shouldBeEmpty()
    }

    @Test
    fun `test initial loading data correctly`() = runBlockingTest {
        // given
        val expected = PagingSource.LoadResult.Page(
            data = listOf(
                "1",
                "Wubba Lubba",
                "3",
                "Wubba Lubba",
                "Dub Dub!",
                "Wubba Lubba",
                "7",
                "Wubba Lubba",
                "9",
                "Wubba LubbaDub Dub!"
            ),
            prevKey = null,
            nextKey = null
        )
        tested.fizzBuzzConfig = FizzBuzzFeedDataSource.FizzBuzzConfig(
            fizzNumber = 2,
            buzzNumber = 5,
            rangeLimit = 10,
            fizzWord = "Wubba Lubba",
            buzzWord = "Dub Dub!"
        )
        // when
        val result =
            tested.load(
                PagingSource.LoadParams.Refresh(
                    key = null,
                    loadSize = 10,
                    placeholdersEnabled = false
                )
            )

        // then
        result `should be equal to` expected
    }
}
