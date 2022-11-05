package com.example.newsapp.utils

import com.example.newsapp.data.remote.NewsResponseDto
import com.example.newsapp.data.ui.NewsResponse

fun NewsResponseDto.Article.toNewsResponse() = NewsResponse(
    articleId = this.newsArticleId,
    author = this.author,
    content = this.content,
    description = this.description,
    publishedAt = this.publishedAt,
    sourceName = this.source.name,
    sourceId = this.source.id,
    title = this.title,
    url = this.url,
    image = this.urlToImage
)