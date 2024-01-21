package com.ibrahimcanerdogan.nves.view.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.card.MaterialCardView
import com.ibrahimcanerdogan.nves.HomeActivity
import com.ibrahimcanerdogan.nves.R
import com.ibrahimcanerdogan.nves.data.model.News
import com.ibrahimcanerdogan.nves.databinding.ActivityHomeBinding
import com.ibrahimcanerdogan.nves.databinding.FragmentNewsBinding
import com.ibrahimcanerdogan.nves.util.NewsCategory
import com.ibrahimcanerdogan.nves.util.Resource
import com.ibrahimcanerdogan.nves.view.adapter.NewsAdapter
import com.ibrahimcanerdogan.nves.view.viewmodel.NewsViewModel
import com.ibrahimcanerdogan.nves.view.viewmodel.NewsViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NewsFragment : Fragment() {

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!

    private val viewModel by lazy {
        ViewModelProvider(this, factory).get(NewsViewModel::class.java)
    }

    @Inject
    lateinit var factory: NewsViewModelFactory

    @Inject
    lateinit var newsAdapter: NewsAdapter

    private var page = 1
    private var totalResults = 0
    private var newsCategory: String = NewsCategory.BUSINESS.param

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Categories configuration.
        binding.setCategoryCard()
        binding.layoutCategory.cardBusiness.setCardBackgroundColor(requireContext().getColor(R.color.MainLigth))

        viewModel.getNewsHeadLines(
            context = requireContext(),
            country = "us",
            category = newsCategory,
            page = page
        )
        viewModel.headlinesData.observe(viewLifecycleOwner, ::setData)

        binding.viewPager.apply {
            adapter = newsAdapter
            registerOnPageChangeCallback(scrollListener)
        }

    }

    private fun setData(resource: Resource<News>?) {
        when (resource) {
            is Resource.Success -> {
                resource.data?.let {
                    newsAdapter.differ.submitList(it.articles?.toList())
                    totalResults = it.totalResults
                    showLoadingAnimation()
                    Log.i(TAG, it.status)
                }
            }

            is Resource.Error -> {
                resource.message?.let { error ->
                    showLoadingAnimation()
                    viewModel.getNewsHeadLines(
                        context = requireContext(),
                        country = "us",
                        category = newsCategory,
                        page = page
                    )
                    Log.e(TAG, error)
                }
            }

            is Resource.Loading -> {
                showLoadingAnimation(true)
            }

            else -> Log.d(TAG, "Resource not found!")
        }
    }

    private val scrollListener = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            if (position == newsAdapter.itemCount - 1) {
                page += 1
                if (page == (totalResults / 20) + 2) page =
                    1 // If max page ((totalResults / 20) + 1) + 1 make return first
                updateNewsHeadlines()
            }
        }
    }

    private fun updateNewsHeadlines() {
        showLoadingAnimation(true)
        Handler(Looper.getMainLooper()).postDelayed({
            viewModel.getNewsHeadLines(
                context = requireContext(),
                country = "us",
                category = newsCategory,
                page = page
            )
        }, 1000)

        binding.viewPager.currentItem = 0
    }

    private fun FragmentNewsBinding.setCategoryCard() {
        layoutCategory.apply {
            setCategoryInfo(cardBusiness, NewsCategory.BUSINESS.param)
            setCategoryInfo(cardEntertainment, NewsCategory.ENTERTAINMENT.param)
            setCategoryInfo(cardGeneral, NewsCategory.GENERAL.param)
            setCategoryInfo(cardHealth, NewsCategory.HEALTH.param)
            setCategoryInfo(cardScience, NewsCategory.SCIENCE.param)
            setCategoryInfo(cardSports, NewsCategory.SPORTS.param)
            setCategoryInfo(cardTechnology, NewsCategory.TECHNOLOGY.param)
        }
    }

    private fun setCategoryInfo(card: MaterialCardView, categoryParam: String) {
        card.setOnClickListener {
            if (newsCategory != categoryParam) {
                setUnSelectCategoryCard()
                page = 1
                newsCategory = categoryParam
                updateNewsHeadlines()
                card.setCardBackgroundColor(requireContext().getColor(R.color.MainLigth))
            }
        }
    }

    private fun setUnSelectCategoryCard() {
        with(binding.layoutCategory) {
            cardBusiness.setCardBackgroundColor(requireContext().getColor(R.color.white))
            cardEntertainment.setCardBackgroundColor(requireContext().getColor(R.color.white))
            cardGeneral.setCardBackgroundColor(requireContext().getColor(R.color.white))
            cardHealth.setCardBackgroundColor(requireContext().getColor(R.color.white))
            cardScience.setCardBackgroundColor(requireContext().getColor(R.color.white))
            cardSports.setCardBackgroundColor(requireContext().getColor(R.color.white))
            cardTechnology.setCardBackgroundColor(requireContext().getColor(R.color.white))
        }
    }

    private fun showLoadingAnimation(isShown: Boolean = false) {
        with(binding) {
            lottieAnimation.visibility = if (isShown) View.VISIBLE else View.INVISIBLE
            viewPager.visibility = if (isShown) View.INVISIBLE else View.VISIBLE
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private val TAG = NewsFragment::class.simpleName.toString()
    }
}