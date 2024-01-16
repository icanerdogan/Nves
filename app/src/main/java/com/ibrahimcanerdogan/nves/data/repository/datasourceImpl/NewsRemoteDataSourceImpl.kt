package com.ibrahimcanerdogan.nves.data.repository.datasourceImpl

import com.ibrahimcanerdogan.nves.data.model.News
import com.ibrahimcanerdogan.nves.data.remote.APIService
import com.ibrahimcanerdogan.nves.data.repository.datasource.NewsRemoteDataSource
import retrofit2.Response

class NewsRemoteDataSourceImpl(
    private val apiService: APIService
) : NewsRemoteDataSource {

    override suspend fun getTopHeadlines(
        country: String,
        category: String,
        page: Int
    ): Response<News> {
        return apiService.getTopHeadlines(country, category, page)
    }
}