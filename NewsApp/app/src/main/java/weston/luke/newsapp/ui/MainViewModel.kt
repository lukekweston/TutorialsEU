package weston.luke.newsapp.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import weston.luke.newsapp.MainApp
import weston.luke.newsapp.data.models.TopNewsResponse

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = getApplication<MainApp>().repository
    private val _newsResponse = MutableStateFlow(TopNewsResponse())
    val topNewsResponse: StateFlow<TopNewsResponse>
        get() = _newsResponse

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun getTopArticles(){
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO){
            _newsResponse.value = repository.getArticles()
        }
        _isLoading.value  = false
    }

}