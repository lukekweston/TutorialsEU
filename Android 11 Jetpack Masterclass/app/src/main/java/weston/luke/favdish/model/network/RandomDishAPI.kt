package weston.luke.favdish.model.network

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query
import weston.luke.favdish.model.entities.RandomDish
import weston.luke.favdish.util.Constants

interface RandomDishAPI {

    @GET(Constants.API_ENDPOINT_RANDOM_DISH)
    fun getRandomDishes(
        @Query(Constants.API_KEY) apiKey: String,
        @Query(Constants.LIMIT_LICENSE) limitLicense: Boolean,
        @Query(Constants.TAGS) tags: String,
        @Query(Constants.NUMBER) number: Int
    ): Single<RandomDish.Recipes>
}