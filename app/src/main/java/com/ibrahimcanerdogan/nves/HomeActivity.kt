package com.ibrahimcanerdogan.nves

import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.ibrahimcanerdogan.nves.data.model.Article
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

    private var page = 1
    private var totalResults = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getNewsHeadLines(context = this, country = "us", category = NewsCategory.TECHNOLOGY.param, page = page)
        viewModel.headlinesData.observe(this, ::setData)

        binding.viewPager.apply {
            adapter = newsAdapter
            registerOnPageChangeCallback(scrollListener)
        }
    }

    private fun setData(resource: Resource<News>?) {
        when(resource) {
            is Resource.Success -> {
                resource.data?.let {
                    newsAdapter.differ.submitList(it.articles?.toList())
                    totalResults = it.totalResults
                    binding.viewPager.currentItem = 0
                    showLoadingAnimation()
                    Log.i("HomeActivity", it.status)
                }
            }
            is Resource.Error -> {
                showLoadingAnimation()
                Log.e("HomeActivity", "Error!")
            }
            is Resource.Loading -> {
                showLoadingAnimation(true)
            }
            else -> Log.d("HomeActivity", "Resource not found!")
        }
    }

    private val scrollListener = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)

            if (position == newsAdapter.itemCount - 1) {
                page += 1
                if (page == (totalResults / 20) + 2 ) page = 1 // If max page ((totalResults / 20) + 1) + 1 make return first

                viewModel.getNewsHeadLines(
                    context = this@HomeActivity,
                    country = "us",
                    category = NewsCategory.BUSINESS.param,
                    page = page
                )
                showLoadingAnimation(true)
            }
        }
    }

    private fun showLoadingAnimation(isShown: Boolean = false) {
        binding.lottieAnimation.visibility = if (isShown) View.VISIBLE else View.INVISIBLE
    }

    companion object {

    }
}