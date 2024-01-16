package com.ibrahimcanerdogan.nves

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.ibrahimcanerdogan.nves.data.model.News
import com.ibrahimcanerdogan.nves.databinding.ActivityHomeBinding
import com.ibrahimcanerdogan.nves.util.NewsCategory
import com.ibrahimcanerdogan.nves.util.Resource
import com.ibrahimcanerdogan.nves.view.adapter.NewsAdapter
import com.ibrahimcanerdogan.nves.view.viewmodel.NewsViewModel
import com.ibrahimcanerdogan.nves.view.viewmodel.NewsViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    private val viewModel by lazy {
        ViewModelProvider(this, factory).get(NewsViewModel::class.java)
    }
    @Inject
    lateinit var factory: NewsViewModelFactory
    @Inject
    lateinit var newsAdapter: NewsAdapter

    private var page = 0
    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getNewsHeadLines(context = this, country = "us", category = NewsCategory.BUSINESS.param, page = page)
        viewModel.headlinesData.observe(this, ::setData)

        binding.viewPager.apply {
            adapter = newsAdapter
        }
    }

    private fun setData(resource: Resource<News>?) {
        when(resource) {
            is Resource.Success -> {
                resource.data?.let {
                    newsAdapter.differ.submitList(it.articles?.toList())
                    Log.i("HomeActivity", it.status)
                }
            }
            is Resource.Error -> {
                Log.e("HomeActivity", "Error!")
            }
            is Resource.Loading -> {
               Log.i("HomeActivity", "Loading!")
            }
            else -> Log.d("HomeActivity", "Resource not found!")
        }
    }
}