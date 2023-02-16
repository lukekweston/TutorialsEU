package weston.luke.newsapp.network


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import weston.luke.newsapp.data.models.TopNewsResponse


interface NewsService {

    @GET("top-headlines")
    suspend fun getTopArticles(
        @Query("country") country: String
    ): TopNewsResponse

    @GET("top-headlines")
    suspend fun getArticlesByCategory(
        @Query("category") category: String
    ): TopNewsResponse


    @GET("everything")
    fun getArticlesBySources(
        @Query("sources") source: String
    ): Call<TopNewsResponse>

    @GET("everything")
    fun getArticlesBySearch(
        @Query("q") query: String
    ): Call<TopNewsResponse>
}