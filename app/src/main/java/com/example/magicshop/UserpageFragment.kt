package com.example.magicshop


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_userpage.*
import kotlinx.android.synthetic.main.list_itemview.*
import okhttp3.ConnectionPool
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
class UserpageFragment : Fragment() {
    private var money: Int = 0
    private var numberList: ArrayList<Int> = arrayListOf()
    private val bonusList: ArrayList<Int> = arrayListOf(1, 1, 2, 1, 1, 2, 2)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_userpage, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val tokenSave = (activity as MainActivity).getSharedPreferences("storage", Activity.MODE_PRIVATE)
        val userJson = tokenSave.getString("userJson","")
        val user = Gson().fromJson(userJson, saveUser::class.java)

        //初始畫面
        val name = user.name
        val balance = user.balance
        val token = user.token
        tv_username.setText("$name")
        tv_usermoney.setText("$balance")
        money = balance
        println("===================$token")

        //進入商店
        btn_shop.setOnClickListener {
            val intent = Intent(this.context, ShopActivity::class.java)
            intent.putExtra("token", token)
            intent.putExtra("money", money)
            startActivityForResult(intent, 0)
        }

        //進入自家魔法庫
        btn_magicList.setOnClickListener {
            val intent = Intent(this.context, MagicListActivity::class.java)
            startActivityForResult(intent, 1)
        }

        //登出
        btn_deleteToken.setOnClickListener {
            val tokenSave = (activity as MainActivity).getSharedPreferences("user", Activity.MODE_PRIVATE)
            val editor = tokenSave.edit()
            editor.remove("token").apply()

            //跳轉登入頁面
            val manager = (activity as MainActivity).supportFragmentManager
            val userTransaction = manager.beginTransaction()
            userTransaction.replace(R.id.framelayout, LoginFragment()).commit()

        }

        //點擊娃娃增加金錢
        upperButton.setOnClickListener {
            checkNumberList2(1)
        }
        downerButton.setOnClickListener {
            checkNumberList2(2)
        }
    }

    //更新餘額
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        data?.extras.let {
            if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
                money = it!!.getInt("updateMoney", 2000)
                tv_usermoney.setText("$money")
            }
        }

    }

    //增加錢幣
    fun checkNumberList2(num: Int){
        if (numberList.isEmpty()) numberList.add(num)
        else if (numberList == arrayListOf(1)) numberList.add(num)
        else if (numberList == arrayListOf(1,1)) numberList.add(num)
        else if (numberList == arrayListOf(1,1,2)) numberList.add(num)
        else if (numberList == arrayListOf(1,1,2,1)) numberList.add(num)
        else if (numberList == arrayListOf(1,1,2,1,1)) numberList.add(num)
        else if (numberList == arrayListOf(1,1,2,1,1,2)){
            numberList.add(num)
            if (numberList == bonusList) {
                money = money + 100
                tv_usermoney.setText(money.toString())
                numberList.clear()
            }
        }
        else{
            if (numberList == arrayListOf(1,1,1)){
                numberList.remove(numberList[0])
            }
            else if (numberList == arrayListOf(1,2)) numberList.clear()
            else{
                numberList.clear()
            }
            numberList.add(num)
        }
    }



}
