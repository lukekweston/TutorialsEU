package weston.luke.newsapp.network


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import weston.luke.newsapp.models.TopNewsResponse


interface NewsService {

    @GET("top-headlines")
    fun getTopArticles(
        @Query("country") country: String
    ): Call<TopNewsResponse>

    @GET("top-headlines")
    fun getArticlesByCategory(
        @Query("category") category: String
    ): Call<TopNewsResponse>
}