package com.example.magicshop

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    private val manager = supportFragmentManager
    private val userpageFragment = UserpageFragment()
    private val loginFragment = LoginFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tokenSave = getSharedPreferences("user", Activity.MODE_PRIVATE)
        val token = tokenSave.getString("token","")

        //判斷
        if (token.isNullOrEmpty() ){
            val loginTransaction = manager.beginTransaction()
            loginTransaction.replace(R.id.framelayout, loginFragment).commit()
        }else{
            val userTransaction = manager.beginTransaction()
            userTransaction.replace(R.id.framelayout, userpageFragment).commit()

        }

    }




}
