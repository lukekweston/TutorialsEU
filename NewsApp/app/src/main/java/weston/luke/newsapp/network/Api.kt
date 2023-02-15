package weston.luke.newsapp.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import weston.luke.newsapp.util.Constants

object Api {

    private val BASE_URL = "https://newsapi.org/v2/"

    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    val logging = HttpLoggingInterceptor()

    val httpClient = OkHttpClient.Builder().apply {
        addInterceptor(
            Interceptor{
                chain ->
                val builder = chain.request().newBuilder()
                //X-api-key comes from https://newsapi.org/ - sends the api as a header rather than in url
                builder.header("X-Api-key", Constants.apiKey)
                return@Interceptor chain.proceed(builder.build())
            }
        )
        logging.level = HttpLoggingInterceptor.Level.BODY
        addNetworkInterceptor(logging)
    }.build()



    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .client(httpClient)
        .build()

    val retrofitService: NewsService by lazy{
        retrofit.create(NewsService::class.java)
    }
}