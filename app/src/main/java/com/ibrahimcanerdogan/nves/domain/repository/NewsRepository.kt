package com.ibrahimcanerdogan.nves.domain.repository

import com.ibrahimcanerdogan.nves.data.model.News
import com.ibrahimcanerdogan.nves.util.Resource

interface NewsRepository {

    suspend fun getNewsHeadlines(country: String, category: String, page: Int) : Resource<News>
}