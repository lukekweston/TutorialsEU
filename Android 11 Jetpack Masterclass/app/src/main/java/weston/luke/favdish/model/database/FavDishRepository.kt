package weston.luke.favdish.model.database

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import weston.luke.favdish.model.entities.FavDish

class FavDishRepository(private val favDishDao: FavDishDao) {

    @WorkerThread
    suspend fun insertFavDishData(favDish: FavDish){
        favDishDao.insertFavDishDetails(favDish)
    }


    val allDishesList: Flow<List<FavDish>> = favDishDao.getAllDishesList()
}