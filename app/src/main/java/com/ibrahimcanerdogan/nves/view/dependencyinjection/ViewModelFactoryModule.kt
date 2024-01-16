package com.ibrahimcanerdogan.nves.view.dependencyinjection

import com.ibrahimcanerdogan.nves.domain.usecase.GetNewsHeadlinesUseCase
import com.ibrahimcanerdogan.nves.view.viewmodel.NewsViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ViewModelFactoryModule {

    @Singleton
    @Provides
    fun provideNewsViewModelFactory(
        getNewsHeadlinesUseCase: GetNewsHeadlinesUseCase
    ): NewsViewModelFactory {
        return NewsViewModelFactory(getNewsHeadlinesUseCase)
    }
}