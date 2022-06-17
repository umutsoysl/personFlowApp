package com.umut.soysal.personapp.domain

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DIModule {

    @Singleton
    @Provides
    fun providePersonUseCase(
        repository: PersonRepository
    ): PersonUseCase {
        return PersonUseCase(repository)
    }
}