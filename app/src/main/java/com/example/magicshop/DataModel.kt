package com.example.magicshop

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


//註冊
data class RegisterRequest(val name: String, val account: String, val password: String, val email: String)
data class RegisterResponse(
    val data: RegisterDetail,
    val message: String,
    val api_token: String)
data class RegisterDetail(
    @Expose
    @SerializedName("name")
    val name: String,
    @Expose
    @SerializedName("email")
    val email: String,
    @Expose
    @SerializedName("account")
    val account: String,
    @Expose
    @SerializedName("api_token")
    val api_token: String,
    @Expose
    @SerializedName("balance")
    val balance: Int,
    @Expose
    @SerializedName("updated_at")
    val updated_at: String,
    @Expose
    @SerializedName("created_at")
    val created_at: String,
    @Expose
    @SerializedName("id")
    val id: Int
)

//登入
data class LoginRequest(val account: String, val password: String)
data class LoginResponse(
    val message: String,
    val data: LoginDetail)
data class LoginDetail(
    @Expose
    @SerializedName("name")
    val name: String,
    @Expose
    @SerializedName("api_token")
    val api_token: String,
    @Expose
    @SerializedName("balance")
    val balance: Int,
    @Expose
    @SerializedName("id")
    val id: Int,
    @Expose
    @SerializedName("role")
    val role: Int,
    @Expose
    @SerializedName("account")
    val account: String,
    @Expose
    @SerializedName("email")
    val email: String,
    @Expose
    @SerializedName("updated_at")
    val updated_at: String,
    @Expose
    @SerializedName("created_at")
    val created_at: String
)

//儲存進sharepreference格式
data class saveUser(val name: String, val balance: Int, val token: String)

//登入會員頁面
data class UserpageResponse(
    val message: String,
    val data: UserpageDetail)
data class UserpageDetail(
    @Expose
    @SerializedName("name")
    val name: String,
    @Expose
    @SerializedName("api_token")
    val api_token: String,
    @Expose
    @SerializedName("balance")
    val balance: Int,
    @Expose
    @SerializedName("id")
    val id: Int,
    @Expose
    @SerializedName("role")
    val role: Int
)

//魔法列表
data class MagicList(
    @Expose
    @SerializedName("data")
    val data: MutableList<MagicItems>
)
data class MagicItems(
    @Expose
    @SerializedName("id")
    val id: Int,
    @Expose
    @SerializedName("name")
    val name: String,
    @Expose
    @SerializedName("price")
    val price: Int,
    @Expose
    @SerializedName("level")
    val level: String,
    @Expose
    @SerializedName("user_id")
    val user_id: Int,
    @Expose
    @SerializedName("store_id")
    val store_id: Int
)
