package com.example.hockeybuff.network

import com.example.hockeybuff.model.NewsItem

data class NewsResponse(val articles: List<Article>)

data class Article(
    val headline: String,
    val byline: String?,
    val source: String?,
    val links: Links,
    val categories: List<Category>?
)

data class Category(
    val type: String?,
    val description: String?
)

data class Links(
    val web: Web
)

data class Web(
    val href: String
)

fun NewsResponse.asNewsItems(): List<NewsItem> {
    return articles.map { 
        val publisher = it.categories?.find { category -> category.type == "publisher" }?.description
        NewsItem(
            title = it.headline,
            source = publisher ?: it.byline ?: it.source ?: "Unknown Source",
            url = it.links.web.href
        )
    }
}