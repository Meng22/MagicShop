package com.example.magicshop

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {
    private val manager = supportFragmentManager
    private val userpageFragment = UserpageFragment()
    private val loginFragment = LoginFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val save = getSharedPreferences("storage", Activity.MODE_PRIVATE)
        val token = save.getString("userToken","")
        println("=============$token")

        //判斷
        if (token.isNullOrEmpty()){
            val loginTransaction = manager.beginTransaction()
            loginTransaction.replace(R.id.framelayout, loginFragment).commit()
        }else{
            val userTransaction = manager.beginTransaction()
            userTransaction.replace(R.id.framelayout, userpageFragment).commit()
            Api.token = token
        }

    }




}
