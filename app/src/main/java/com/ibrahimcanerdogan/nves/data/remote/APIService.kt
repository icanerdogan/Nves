package com.ibrahimcanerdogan.nves.data.remote

import com.ibrahimcanerdogan.nves.data.model.News
import com.ibrahimcanerdogan.nves.util.NewsCategory
import net.cachapa.expandablelayout.BuildConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {

    @GET("v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("country")
        country: String = "us",
        @Query("category")
        category: String = NewsCategory.BUSINESS.param,
        @Query("page")
        page: Int,
        @Query("apiKey")
        apiKey: String = "b4984ae3a8ae47549738b33257fe9ca9"
    ): Response<News>

}