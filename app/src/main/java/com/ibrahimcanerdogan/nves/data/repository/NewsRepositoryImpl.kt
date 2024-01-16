package com.ibrahimcanerdogan.nves.data.repository

import com.ibrahimcanerdogan.nves.data.model.News
import com.ibrahimcanerdogan.nves.data.repository.datasource.NewsRemoteDataSource
import com.ibrahimcanerdogan.nves.domain.repository.NewsRepository
import com.ibrahimcanerdogan.nves.util.Resource

class NewsRepositoryImpl(
    private val newsRemoteDataSource: NewsRemoteDataSource
) : NewsRepository {

    override suspend fun getNewsHeadlines(country: String, category: String, page: Int): Resource<News> {
        val apiResult = newsRemoteDataSource.getTopHeadlines(country, category, page)
        if (apiResult.isSuccessful) {
            apiResult.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(apiResult.message())
    }
}