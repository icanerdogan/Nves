package com.ibrahimcanerdogan.nves.view.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ibrahimcanerdogan.nves.data.model.News
import com.ibrahimcanerdogan.nves.domain.usecase.GetNewsHeadlinesUseCase
import com.ibrahimcanerdogan.nves.util.NetworkController
import com.ibrahimcanerdogan.nves.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getNewsHeadlinesUseCase: GetNewsHeadlinesUseCase
) : ViewModel() {

    private val headlines: MutableLiveData<Resource<News>> = MutableLiveData()
    val headlinesData : LiveData<Resource<News>>
        get() = headlines

    fun getNewsHeadLines(
        context: Context? = null,
        country: String,
        category: String,
        page: Int
    ) = viewModelScope.launch(Dispatchers.IO) {

        headlines.postValue(Resource.Loading())
        try {
            if (NetworkController.isNetworkAvailable(context)) {
                val apiResult = getNewsHeadlinesUseCase.execute(country, category, page)
                headlines.postValue(apiResult)
            } else {
                headlines.postValue(Resource.Error("Internet is not available!"))
            }
        } catch (e: Exception) {
            headlines.postValue(Resource.Error(e.message.toString()))
        }
    }

}