package weston.luke.favdish.model.database

import androidx.room.Dao
import androidx.room.Insert
import weston.luke.favdish.model.entities.FavDish


@Dao
interface FavDishDao {

//    Make sure you use suspend as in a coroutine
    @Insert
    suspend fun insertFavDishDetails(favDish: FavDish)

}