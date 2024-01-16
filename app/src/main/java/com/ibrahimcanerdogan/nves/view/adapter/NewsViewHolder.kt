package com.ibrahimcanerdogan.nves.view.adapter

import androidx.recyclerview.widget.RecyclerView
import com.ibrahimcanerdogan.nves.data.model.Article
import com.ibrahimcanerdogan.nves.databinding.ItemNewsBinding

class NewsViewHolder(
    val binding : ItemNewsBinding,
    private val onNewsItemClick : ((Article) -> Unit)?
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(article: Article) {
        binding.apply {
            card.setOnClickListener { onNewsItemClick?.invoke(article) }
            textTitle.text = article.articleTitle
            textDate.text = article.articlePublishedAt
            textContent.text = article.articleContent ?: ""
        }
    }
}