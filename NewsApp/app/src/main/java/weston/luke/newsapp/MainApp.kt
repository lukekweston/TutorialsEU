package weston.luke.newsapp

import android.app.Application
import weston.luke.newsapp.network.Api
import weston.luke.newsapp.network.NewsManager
import weston.luke.newsapp.data.Repository

class MainApp : Application() {

    private val manager by lazy {
        NewsManager(Api.retrofitService)
    }

    val repository by lazy{
        Repository(manager)
    }


}