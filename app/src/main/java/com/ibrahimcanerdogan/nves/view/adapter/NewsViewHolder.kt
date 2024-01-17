package com.ibrahimcanerdogan.nves.view.adapter

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ibrahimcanerdogan.nves.R
import com.ibrahimcanerdogan.nves.data.model.Article
import com.ibrahimcanerdogan.nves.databinding.ItemNewsBinding

class NewsViewHolder(
    private val binding : ItemNewsBinding,
    private val onNewsItemClick : ((Article) -> Unit)?
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(article: Article) {
        binding.apply {
            card.setOnClickListener { onNewsItemClick?.invoke(article) }
            textTitle.text = article.articleTitle
            textDate.text = article.articlePublishedAt!!.split("T")[0]
            textDescription.text = article.articleDescription ?: ""

            setImage(article, imageViewBackground)
            setImage(article, imageViewThumbnail)

        }
    }

    private fun setImage(article: Article, imageView: ImageView) {
        Glide.with(binding.root.context)
            .load(if(!article.articleUrlToImage.isNullOrEmpty()) article.articleUrlToImage else R.drawable.news_background)
            .into(imageView)
    }
}