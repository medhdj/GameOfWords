package com.medhdj.gow.di

import com.medhdj.business.fizzbuzz.FizzBuzzResultRepository
import com.medhdj.business.fizzbuzz.GetFizzBuzzResultUseCase
import com.medhdj.business.fizzbuzz.GetFizzBuzzResultUseCaseImpl
import com.medhdj.data.fizzbuzz.FizzBuzzFeedDataSource
import com.medhdj.data.fizzbuzz.FizzBuzzResultRepositoryImpl

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class FizzBuzzModule {

    @Provides
    fun provideGetFizzBuzzResultUseCase(fizzBuzzResultRepository: FizzBuzzResultRepository):
            GetFizzBuzzResultUseCase = GetFizzBuzzResultUseCaseImpl(fizzBuzzResultRepository)

    @Provides
    fun provideFizzBuzzResultRepository(fizzBuzzFeedDataSource: FizzBuzzFeedDataSource):
            FizzBuzzResultRepository = FizzBuzzResultRepositoryImpl(fizzBuzzFeedDataSource)

    @Provides
    fun provideFizzBuzzFeedDataSource():
            FizzBuzzFeedDataSource = FizzBuzzFeedDataSource()
}
