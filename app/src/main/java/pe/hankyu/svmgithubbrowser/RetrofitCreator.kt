package pe.hankyu.svmgithubbrowser

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitCreator {

    companion object {
        val API_ROOT = "https://api.github.com"

        private fun defaultRetrofit(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(API_ROOT)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        }

        fun <T> create(service: Class<T>): T {
            return defaultRetrofit().create(service)
        }
    }
}