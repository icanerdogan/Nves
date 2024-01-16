package com.ibrahimcanerdogan.nves.data.repository.datasource

import com.ibrahimcanerdogan.nves.data.model.News
import retrofit2.Response

interface NewsRemoteDataSource {
    suspend fun getTopHeadlines(country : String, category: String, page : Int): Response<News>

}