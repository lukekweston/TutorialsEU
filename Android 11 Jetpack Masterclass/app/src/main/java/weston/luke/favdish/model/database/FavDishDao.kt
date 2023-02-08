package weston.luke.favdish.model.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import weston.luke.favdish.model.entities.FavDish


@Dao
interface FavDishDao {

//    Make sure you use suspend as in a coroutine
    @Insert
    suspend fun insertFavDishDetails(favDish: FavDish)


    @Query("SELECT * FROM FAV_DISHES_TABLE ORDER BY ID")
    fun getAllDishesList() : Flow<List<FavDish>>

    @Update
    suspend fun updateFavDishDetails(favDish: FavDish)

    @Query("SELECT * FROM FAV_DISHES_TABLE WHERE favourite_dish = 1")
    fun getAllFavoriteDishesList() : Flow<List<FavDish>>

    @Delete
    suspend fun deleteFavDishDetails(favDish: FavDish)

}