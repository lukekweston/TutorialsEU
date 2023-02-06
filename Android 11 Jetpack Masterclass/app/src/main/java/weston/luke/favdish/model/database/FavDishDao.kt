package weston.luke.favdish.model.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import weston.luke.favdish.model.entities.FavDish


@Dao
interface FavDishDao {

//    Make sure you use suspend as in a coroutine
    @Insert
    suspend fun insertFavDishDetails(favDish: FavDish)


    @Query("SELECT * FROM FAV_DISHES_TABLE ORDER BY ID")
    fun getAllDishesList() : Flow<List<FavDish>>

}