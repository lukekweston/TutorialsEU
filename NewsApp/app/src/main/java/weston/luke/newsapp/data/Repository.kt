package weston.luke.newsapp.data

import weston.luke.newsapp.network.NewsManager

class Repository(private val manager: NewsManager) {
    suspend fun getArticles() = manager.getArticles("nz")
    suspend fun getArticlesByCategory(category: String) = manager.getArticlesByCategory(category)
}