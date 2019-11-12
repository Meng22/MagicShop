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
        //初始畫面
        login_layout.visibility = View.VISIBLE
        btn_login.isSelected = true
        register_layout.visibility = View.GONE
        btn_sign.isSelected = false

        btn_sign.setOnClickListener {
            register_layout.visibility = View.VISIBLE
            btn_sign.isSelected = true
            login_layout.visibility = View.GONE
            btn_login.isSelected = false

            if (register_name.text.isBlank() || register_account.text.isBlank() || register_password.text.isBlank() || register_email.text.isBlank()){
                Toast.makeText(this.context, "請輸入註冊資料", Toast.LENGTH_SHORT).show()
            }else{
                Api.service.register(RegisterRequest("${register_name.text}", "${register_account.text}", "${register_password.text}", "${register_email.text}")).enqueue(
                    object : Callback<RegisterResponse> {
                        override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                            println("register failed")
                        }
                        override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                            val register_token = response.body()!!.api_token
                            println("$register_token")

                            //註冊儲存token
                            val tokenSave = (activity as MainActivity).getSharedPreferences("user", Activity.MODE_PRIVATE)
                            val editor = tokenSave.edit()
                            editor.putString("token", "$register_token").apply()

                            //跳轉使用者頁面
                            val manager = (activity as MainActivity).supportFragmentManager
                            val userTransaction = manager.beginTransaction()
                            userTransaction.replace(R.id.framelayout, UserpageFragment()).commit()
                        }
                    }
                )
            }
        }

        btn_login.setOnClickListener {
            login_layout.visibility = View.VISIBLE
            btn_login.isSelected = true
            register_layout.visibility = View.GONE
            btn_sign.isSelected = false

            if (login_account.text.isBlank() || login_password.text.isBlank()){
                Toast.makeText(this.context, "請輸入帳號密碼", Toast.LENGTH_SHORT).show()
            }else{
                Api.service.login(LoginRequest("${login_account.text}","${login_password.text}")).enqueue(
                    object : Callback<LoginResponse>{
                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        println("==========$t")
                    }
                    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                        val login_token = response.body()!!.data.api_token
                        val login_name = response.body()!!.data.name
                        val login_money = response.body()!!.data.balance
                        println("==================$login_token")

                        val tokenSave = (activity as MainActivity).getSharedPreferences("storage", Activity.MODE_PRIVATE)
                        val editor = tokenSave.edit()
                        val userJson = Gson().toJson(saveUser(login_name, login_money, login_token))
                        editor.putString("userJson", userJson).apply()

                        //儲存完token後，切換使用者頁面
                        val manager = (activity as MainActivity).supportFragmentManager
                        val userTransaction = manager.beginTransaction()
                        userTransaction.replace(R.id.framelayout, UserpageFragment()).commit()

                    }
                })
            }
        }
    }


}
