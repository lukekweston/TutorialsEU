package weston.luke.favdish.application

import android.app.Application
import weston.luke.favdish.model.database.FavDishRepository
import weston.luke.favdish.model.database.FavDishRoomDatabase

class FavDishApplication : Application() {

    private val database by lazy { FavDishRoomDatabase.getDatabase(this@FavDishApplication)}

    val repository by lazy { FavDishRepository(database.favDishDao()) }
}