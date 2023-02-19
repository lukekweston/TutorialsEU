package weston.luke.newsapp.ui

import android.app.Application
import androidx.compose.runtime.*
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import weston.luke.newsapp.MainApp
import weston.luke.newsapp.data.ArticleCategory
import weston.luke.newsapp.data.getArticleCategory
import weston.luke.newsapp.data.models.TopNewsArticle
import weston.luke.newsapp.data.models.TopNewsResponse

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = getApplication<MainApp>().repository
    private val _newsResponse = MutableStateFlow(TopNewsResponse())
    val topNewsResponse: StateFlow<TopNewsResponse>
        get() = _newsResponse

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun getTopArticles() {
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            _newsResponse.value = repository.getArticles()
        }
        _isLoading.value = false
    }

    private val _getArticleByCategory = MutableStateFlow(TopNewsResponse())
    val getArticleCategory: StateFlow<TopNewsResponse>
        get() = _getArticleByCategory

    private val _selectedCategory: MutableStateFlow<ArticleCategory?> = MutableStateFlow(null)
    val selectedCategory: StateFlow<ArticleCategory?>
        get() = _selectedCategory


    fun getArticlesByCategory(category: String) {
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            _getArticleByCategory.value = repository.getArticlesByCategory(category)
        }
        _isLoading.value = false
    }

    val sourceName = MutableStateFlow("techcrunch")
    private val _getArticleBySource = MutableStateFlow(TopNewsResponse())
    val getArticleBySource: StateFlow<TopNewsResponse>
        get() = _getArticleBySource

    val query = MutableStateFlow("")
    private val _searchedNewsResponse = MutableStateFlow(TopNewsResponse())
    val getSearchedResponse: StateFlow<TopNewsResponse>
        get() = _searchedNewsResponse

    fun selectedCategoryChanged(category: String) {
        val newCategory = getArticleCategory(category)
        _selectedCategory.value = newCategory
    }


    fun getArticlesBySource(){
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO){
            _getArticleBySource.value = repository.getArticlesBySource(sourceName.value)
        }
        _isLoading.value = false
    }

    fun getSearchedArticles(query : String){
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO){
            _searchedNewsResponse.value = repository.getArticlesBySearch(query)
        }
        _isLoading.value = false
    }

}