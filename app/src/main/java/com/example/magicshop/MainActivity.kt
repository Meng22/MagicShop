package com.example.magicshop

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var money: Int = 2000  //初始金額
    private var numberList: ArrayList<Int> = arrayListOf()
    private val bonusList: ArrayList<Int> = arrayListOf(1, 1, 2, 1, 1, 2, 2)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv_money.setText(money.toString())

        //進入商店
        btn_shop.setOnClickListener {
            val intent = Intent(this, ShopActivity::class.java)
            intent.putExtra("money", money)
            startActivityForResult(intent, 0)
        }

        //進入自家魔法庫
        btn_magicList.setOnClickListener {
            val intent = Intent(this, MagicListActivity::class.java)
            startActivityForResult(intent, 1)
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
                tv_money.setText("$money")
            }
        }

    }
//    fun checkNumberList(){
//        if (numberList.size >= 7){
//            numberList.clear()
//            btn_refresh.visibility = View.GONE
//        }
//    }

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
                tv_money.setText(money.toString())
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
