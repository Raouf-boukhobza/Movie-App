package com.raouf.movieapp.di

import com.raouf.movieapp.data.repository.MovieRepositoryImpl
import com.raouf.movieapp.domain.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindsRepository(
        movieRepositoryImpl: MovieRepositoryImpl
    ) : MovieRepository
}