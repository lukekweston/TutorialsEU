package weston.luke.favdish.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import weston.luke.favdish.model.entities.FavDish

//Version will need to be bumped after every database change
//Keeps track of migrations
@Database(entities = [FavDish::class], version = 1)
abstract class FavDishRoomDatabase : RoomDatabase() {
    companion object{
//        Needs to be a singleton as there should only ever be one instance max of the database
        @Volatile
        private var INSTANCE: FavDishRoomDatabase? = null

        fun getDatabase(context: Context): FavDishRoomDatabase{
//            If INSTANCE is not null, then return it
//            If it is, then create the database
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FavDishRoomDatabase::class.java,
                    "fav_dish_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
//                return the instance
                instance
            }
        }

    }
}