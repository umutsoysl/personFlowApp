package com.umut.soysal.personapp.data

import com.umut.soysal.personapp.domain.PersonRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DIModule {

    @Provides
    @Singleton
    fun providePersonRepository(
        personDataSource: PersonDataSource
    ): PersonRepository = PersonRepositoryImpl(personDataSource)
}