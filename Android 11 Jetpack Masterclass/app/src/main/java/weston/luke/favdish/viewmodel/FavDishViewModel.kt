package weston.luke.favdish.viewmodel

import androidx.annotation.WorkerThread
import androidx.lifecycle.*
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

    val allDishesList: LiveData<List<FavDish>> = repository.allDishesList.asLiveData()

    @WorkerThread
    fun update(dish: FavDish) = viewModelScope.launch {
        repository.updateFavDishData(favDish = dish)
    }

    val allFavoriteDishes: LiveData<List<FavDish>> = repository.allFavoriteDishes.asLiveData()

    @WorkerThread
    fun delete(dish: FavDish) = viewModelScope.launch {
        repository.deleteFavDishDetails(favDish = dish)
    }

    fun getFilteredDishes(value: String): LiveData<List<FavDish>> = repository.getFilteredDishesList(value).asLiveData()
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