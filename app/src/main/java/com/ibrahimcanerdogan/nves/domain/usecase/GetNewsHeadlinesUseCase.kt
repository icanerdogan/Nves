package com.ibrahimcanerdogan.nves.domain.usecase

import com.ibrahimcanerdogan.nves.data.model.News
import com.ibrahimcanerdogan.nves.domain.repository.NewsRepository
import com.ibrahimcanerdogan.nves.util.Resource

class GetNewsHeadlinesUseCase(private val newsRepository: NewsRepository) {

    suspend fun execute(country : String, category: String, page : Int): Resource<News> {
        return newsRepository.getNewsHeadlines(country, category, page)
    }
}