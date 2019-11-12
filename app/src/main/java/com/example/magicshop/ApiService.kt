package com.example.magicshop

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    //顯示使用者魔法
    @GET("/api/tags")
    fun showMagic(@Header("Authorization") token: String): Call<MagicList>

    //註冊
    @POST("/api/register")
    fun register(@Body registerRequest: RegisterRequest): Call<RegisterResponse>

    //登入
    @POST("/api/login")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>

    //登入後會員畫面
    @GET("/api/accounts")
    fun userpage(@Header("Authorization") token: String): Call<UserpageResponse>

}