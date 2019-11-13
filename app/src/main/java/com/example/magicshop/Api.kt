package com.example.magicshop

import android.util.Log
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import okhttp3.logging.HttpLoggingInterceptor

//只存在一個實體
//時間
//singleton

object Api {
    //在裡面的變數都是唯一的
    var token: String? = null

    val service: ApiService by lazy {  //需要使用到時，只會初始化一次
        Log.d("API","init retrofit")

        val logging = HttpLoggingInterceptor()
        logging.level = (HttpLoggingInterceptor.Level.BODY)

        val myOkHttpClient = OkHttpClient.Builder()
            .readTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(object :Interceptor{
            override fun intercept(chain: Interceptor.Chain): Response {
                val original = chain.request()

                //定義好，上傳可能有問題
                val requestBuilder = original.newBuilder()
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")

                if(token != null){
                    requestBuilder.addHeader("Authorization","Bearer $token")
                }
                return chain.proceed(requestBuilder.build())
            }
        })
            .addInterceptor(logging)
            .build()

        //retrofit實體
        return@lazy Retrofit.Builder()
            .baseUrl("https://f5234a33.ngrok.io")
            .client(myOkHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}