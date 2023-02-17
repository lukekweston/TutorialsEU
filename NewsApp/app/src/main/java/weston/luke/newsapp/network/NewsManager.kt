package weston.luke.newsapp.network

import android.util.Log
import androidx.compose.runtime.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import weston.luke.newsapp.data.ArticleCategory
import weston.luke.newsapp.data.getArticleCategory
import weston.luke.newsapp.data.models.TopNewsResponse


class NewsManager(private val service: NewsService) {

    private val _newsResponse = mutableStateOf(TopNewsResponse())
    //Just state as this end point is called once on start up
    val newsResponse: State<TopNewsResponse>
        @Composable get() = remember {
            _newsResponse
        }

    val sourceName = mutableStateOf("abc-news")

    private val _getArticleBySource = mutableStateOf(TopNewsResponse())
    //Mutable state as the get category end point is hit everytime the user presses a category
    val getArticleSource: MutableState<TopNewsResponse>
        @Composable get() = remember {
            _getArticleBySource
        }



    val query = mutableStateOf("")

    private val _searchedNewsResponse = mutableStateOf(TopNewsResponse())
    val searchedNewsResponse : MutableState<TopNewsResponse>
        @Composable get() = remember {
            _searchedNewsResponse
        }

    val selectedCategory: MutableState<ArticleCategory?> = mutableStateOf(null)

    suspend fun getArticles(country: String): TopNewsResponse = withContext(Dispatchers.IO) {
        service.getTopArticles(country = country)

    }

    fun getArticleSource(){
        val service = Api.retrofitService.getArticlesBySources(sourceName.value)
        service.enqueue(object : Callback<TopNewsResponse> {
            override fun onResponse(
                call: Call<TopNewsResponse>,
                response: Response<TopNewsResponse>
            ) {
                if (response.isSuccessful) {
                    _getArticleBySource.value = response.body()!!
                    Log.d("source", "${_getArticleBySource.value}")
                } else {
                    Log.d("error", "${response.code()}")
                }
            }

            override fun onFailure(call: Call<TopNewsResponse>, t: Throwable) {
                Log.d("error", "${t.printStackTrace()}")
            }
        })
    }

    suspend fun getArticlesByCategory(category: String): TopNewsResponse = withContext(Dispatchers.IO) {
        service.getArticlesByCategory(category = category)
    }

    fun getSearchedArticles(query: String){
        val service = Api.retrofitService.getArticlesBySearch(query)
        service.enqueue(object : Callback<TopNewsResponse> {
            override fun onResponse(
                call: Call<TopNewsResponse>,
                response: Response<TopNewsResponse>
            ) {
                if (response.isSuccessful) {
                    _searchedNewsResponse.value = response.body()!!
                    Log.d("search", "${_searchedNewsResponse.value}")
                } else {
                    Log.d("error", "${response.code()}")
                }
            }

            override fun onFailure(call: Call<TopNewsResponse>, t: Throwable) {
                Log.d("error", "${t.printStackTrace()}")
            }
        })
    }

    //Update the currently selected Category to be the category passed in
    fun onSelectedCategoryChanged(category: String){
        val newCategory = getArticleCategory(category = category)
        selectedCategory.value = newCategory
    }
}