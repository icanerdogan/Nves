package com.ibrahimcanerdogan.nves.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serial

data class Article(
    @SerializedName("author")
    val articleAuthor: String?,
    @SerializedName("content")
    val articleContent: String?,
    @SerializedName("description")
    val articleDescription: String?,
    @SerializedName("publishedAt")
    val articlePublishedAt: String?,
    @SerializedName("source")
    val articleSource: Source?,
    @SerializedName("title")
    val articleTitle: String?,
    @SerializedName("url")
    val articleUrl: String?,
    @SerializedName("urlToImage")
    val articleUrlToImage: String?
)