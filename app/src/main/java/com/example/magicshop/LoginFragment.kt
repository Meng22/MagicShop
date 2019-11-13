package com.example.magicshop


import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_login.*
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * A simple [Fragment] subclass.
 *
 */
class LoginFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loginPage()  //初始畫面

        btn_sign.setOnClickListener {
            registerPage()

            if (register_name.text.isBlank() || register_account.text.isBlank() || register_password.text.isBlank() || register_email.text.isBlank()){
                Toast.makeText(this.context, "請輸入註冊資料", Toast.LENGTH_SHORT).show()
            }else{
                Api.service.register(RegisterRequest("${register_name.text}", "${register_account.text}", "${register_password.text}", "${register_email.text}")).enqueue(
                    object : Callback<RegisterResponse> {
                        override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                            println("==========$t")
                        }
                        override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                            println("=============${response.message()}")
                            if (response.isSuccessful){  //安全性
                                val responsebody = response.body()
                                val register_token = responsebody!!.api_token
                                val message = responsebody.message
                                println("==========$register_token")
                                println("==========$message")
                                Api.token = register_token

                                //註冊完跳登入頁
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                                loginPage()
                            }
                        }
                    }
                )
            }
        }

        btn_login.setOnClickListener {
            loginPage()

            if (login_account.text.isBlank() || login_password.text.isBlank()){
                Toast.makeText(this.context, "請輸入帳號及密碼", Toast.LENGTH_SHORT).show()
            }else{
                Api.service.login(LoginRequest("${login_account.text}","${login_password.text}")).enqueue(
                    object : Callback<LoginResponse>{
                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        println("==========$t")
                    }
                    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                        if(response.code() == 500){
                            Toast.makeText(context, "無法登入", Toast.LENGTH_SHORT).show()
                        }
                        else if (response.isSuccessful){
                            val responsebody = response.body()
                            val login_token = responsebody!!.data.api_token
                            val message = responsebody.message
                            Api.token = login_token
                            println("================$login_token")

                            //儲存完token後，切換使用者頁面
                            if (chk_remember.isChecked){
                                val tokenSave = (activity as MainActivity).getSharedPreferences("storage", Activity.MODE_PRIVATE)
                                val editor = tokenSave.edit()
                                println("================$login_token")
                                editor.putString("userToken", login_token).apply()
                            }
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

                            val manager = (activity as MainActivity).supportFragmentManager
                            val userTransaction = manager.beginTransaction()
                            userTransaction.replace(R.id.framelayout, UserpageFragment()).commit()
                        }
                    }
                })
            }
        }
    }
    fun loginPage(){
        login_layout.visibility = View.VISIBLE
        btn_login.isSelected = true
        register_layout.visibility = View.GONE
        btn_sign.isSelected = false
    }
    fun registerPage(){
        register_layout.visibility = View.VISIBLE
        btn_sign.isSelected = true
        login_layout.visibility = View.GONE
        btn_login.isSelected = false
    }


}
