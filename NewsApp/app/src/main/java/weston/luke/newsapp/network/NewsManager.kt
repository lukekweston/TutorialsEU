package weston.luke.newsapp.network

import android.util.Log
import androidx.compose.runtime.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import weston.luke.newsapp.data.ArticleCategory
import weston.luke.newsapp.data.getAllArticleCategory
import weston.luke.newsapp.data.getArticleCategory
import weston.luke.newsapp.models.TopNewsResponse
import weston.luke.newsapp.util.Constants


class NewsManager {

    private val _newsResponse = mutableStateOf(TopNewsResponse())
    //Just state as this end point is called once on start up
    val newsResponse: State<TopNewsResponse>
        @Composable get() = remember {
            _newsResponse
        }

    private val _getArticleByCategory = mutableStateOf(TopNewsResponse())
    //Mutable state as the get category end point is hit everytime the user presses a category
    val getArticleCategory: MutableState<TopNewsResponse>
        @Composable get() = remember {
            _getArticleByCategory
        }

    val selectedCategory: MutableState<ArticleCategory?> = mutableStateOf(null)
    init {
        getArticles()
    }

    private fun getArticles() {
        val service = Api.retrofitService.getTopArticles("nz")
        service.enqueue(object : Callback<TopNewsResponse> {
            override fun onResponse(
                call: Call<TopNewsResponse>,
                response: Response<TopNewsResponse>
            ) {
                if (response.isSuccessful) {
                    _newsResponse.value = response.body()!!
                    Log.d("news", "${_newsResponse.value}")
                } else {
                    Log.d("error", "${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<TopNewsResponse>, t: Throwable) {
                Log.d("error", "${t.printStackTrace()}")
            }
        })
    }

    fun getArticlesByCategory(category: String){
        val service = Api.retrofitService.getArticlesByCategory(category)
        service.enqueue(object : Callback<TopNewsResponse> {
            override fun onResponse(
                call: Call<TopNewsResponse>,
                response: Response<TopNewsResponse>
            ) {
                if (response.isSuccessful) {
                    _getArticleByCategory.value = response.body()!!
                    Log.d("category", "${_getArticleByCategory.value}")
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