package com.example.magicshop


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_userpage.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

        Api.service.userInfo().enqueue(object : Callback<UserpageResponse>{
            override fun onFailure(call: Call<UserpageResponse>, t: Throwable) {
                println("=============$t")
            }
            override fun onResponse(call: Call<UserpageResponse>, response: Response<UserpageResponse>) {
                val responsebody = response.body()
                val user = responsebody!!.data

                //初始畫面
                val name = user.name
                val balance = user.balance
                tv_username.setText("$name")
                tv_usermoney.setText("$balance")
                money = balance

            }
        })


        //進入商店
        btn_shop.setOnClickListener {
            val intent = Intent(this.context, ShopActivity::class.java)
            intent.putExtra("money", money)
            startActivityForResult(intent, 0)
        }

        //進入自家魔法庫
        btn_magicList.setOnClickListener {
            val intent = Intent(this.context, MagicListActivity::class.java)
            startActivityForResult(intent, 1)
        }

        //登出
        btn_logout.setOnClickListener {

            //清除token
            val tokenSave = (activity as MainActivity).getSharedPreferences("storage", Activity.MODE_PRIVATE)
            val editor = tokenSave.edit()
            editor.remove("userToken").apply()

            //跳轉登入頁面
            Toast.makeText(context, "登出成功", Toast.LENGTH_SHORT).show()
            val manager = (activity as MainActivity).supportFragmentManager
            val userTransaction = manager.beginTransaction()
            userTransaction.replace(R.id.framelayout, LoginFragment()).commit()

        }

        //點擊娃娃
        upperButton.setOnClickListener {
            checkNumberList2(1)
        }
        downerButton.setOnClickListener {
            checkNumberList2(2)
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
