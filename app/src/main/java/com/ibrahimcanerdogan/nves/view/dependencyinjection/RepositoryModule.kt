package com.ibrahimcanerdogan.nves.view.dependencyinjection

import com.ibrahimcanerdogan.nves.data.repository.NewsRepositoryImpl
import com.ibrahimcanerdogan.nves.data.repository.datasource.NewsRemoteDataSource
import com.ibrahimcanerdogan.nves.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideNewsRepository(
        newsRemoteDataSource: NewsRemoteDataSource
    ) : NewsRepository {
        return NewsRepositoryImpl(newsRemoteDataSource)
    }
}