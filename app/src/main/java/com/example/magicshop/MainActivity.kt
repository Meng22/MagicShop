package com.example.magicshop

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var money: Int = 2000  //初始金額
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tv_money.setText(money.toString())

        btn_shop.setOnClickListener {
            val intent = Intent(this, ShopActivity::class.java)
            intent.putExtra("money", money)
            startActivityForResult(intent, 0)
        }

        btn_magicList.setOnClickListener {
            val intent = Intent(this, MagicListActivity::class.java)
            startActivityForResult(intent, 1)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        data?.extras.let {
            if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
                money = it!!.getInt("updateMoney", 2000)
                tv_money.setText("$money")
            }
        }

    }
}
