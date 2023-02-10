package weston.luke.favdish.model.network

import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

import weston.luke.favdish.model.entities.RandomDish
import weston.luke.favdish.util.Constants
import weston.luke.favdish.util.ConstantsHidden

class RandomDishApiService {

    private val api = Retrofit.Builder().baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()
        .create(RandomDishAPI::class.java)

    fun getRandomDish(): Single<RandomDish.Recipes> {
        return api.getRandomDishes(
            ConstantsHidden.API_KEY_VALUE,
            Constants.LIMIT_LICENSE_VALUE,
            Constants.TAGS_VALUE,
            Constants.NUMBER_VALUE
        )
    }
}