package com.ibrahimcanerdogan.nves.view.dependencyinjection

import com.ibrahimcanerdogan.nves.data.remote.APIService
import com.ibrahimcanerdogan.nves.data.repository.datasource.NewsRemoteDataSource
import com.ibrahimcanerdogan.nves.data.repository.datasourceImpl.NewsRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteDataModule {

    @Singleton
    @Provides
    fun provideNewsRemoteDataSource(
        newsAPIService: APIService
    ): NewsRemoteDataSource {
        return NewsRemoteDataSourceImpl(newsAPIService)
    }
}