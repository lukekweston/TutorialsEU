package weston.luke.favdish.viewmodel

import androidx.annotation.WorkerThread
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import weston.luke.favdish.model.database.FavDishRepository
import weston.luke.favdish.model.entities.FavDish
import java.lang.IllegalArgumentException

class FavDishViewModel(private val repository: FavDishRepository) :ViewModel() {

//    Start coroutine and insert favDish
    @WorkerThread
    fun insert(dish: FavDish) = viewModelScope.launch {
        repository.insertFavDishData(favDish = dish)
    }
}

class FavDishViewModelFactory(private val repository: FavDishRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(FavDishViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return FavDishViewModel(repository) as T

        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}