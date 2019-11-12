package com.example.magicshop

import android.util.Log
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

//只存在一個實體
//時間
//singleton

object Api {
//在裡面的變數都是唯一的
    val service: ApiService by lazy {
        Log.d("API","init retrofit")
        val myOkHttpClient = OkHttpClient.Builder()
            .readTimeout(10, TimeUnit.SECONDS)
            .build()

        //retrofit實體
        return@lazy Retrofit.Builder()
            .baseUrl("https://d6b3f29b.ngrok.io")
            .client(myOkHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}