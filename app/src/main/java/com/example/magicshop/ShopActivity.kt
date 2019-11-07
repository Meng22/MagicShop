package com.example.magicshop

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_shop.*

class ShopActivity : AppCompatActivity() {
    private var money: Int = 0
    private val shopAdapter1 = ShopAdapter_list()
    private val shopAdapter2 = ShopAdapter_grid()
    private var mode = false
    private var checkList = arrayListOf(1)
    lateinit var storage1: SharedPreferences
    lateinit var storage2: SharedPreferences
    lateinit var storage3: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop)
        storage1 = getSharedPreferences("LEVEL1", Activity.MODE_PRIVATE)
        storage2 = getSharedPreferences("LEVEL2", Activity.MODE_PRIVATE)
        storage3 = getSharedPreferences("LEVEL3", Activity.MODE_PRIVATE)

        money = intent.getIntExtra("money", 2000) //defaultValue 默認值
        tv_money.text = money.toString()
        //初始畫面
        switchList(1)
        menu_L1.isSelected = true
        menu_list.isSelected = true

        //切換魔法等級
        menu_L1.setOnClickListener {
            switchList(1)
            checkType(1)
            menu_L1.isSelected = true
            menu_L2.isSelected = false
            menu_L3.isSelected = false

        }
        menu_L2.setOnClickListener {
            switchList(2)
            checkType(2)
            menu_L2.isSelected = true
            menu_L1.isSelected = false
            menu_L3.isSelected = false

        }
        menu_L3.setOnClickListener {
            switchList(3)
            checkType(3)
            menu_L3.isSelected = true
            menu_L1.isSelected = false
            menu_L2.isSelected = false
        }

        //切換檢視狀態
        menu_list.setOnClickListener {
            mode = false
            changeType()
            menu_list.isSelected = true
            menu_grid.isSelected = false
        }
        menu_grid.setOnClickListener {
            mode = true
            changeType()
            menu_grid.isSelected = true
            menu_list.isSelected = false
        }
    }

    override fun onBackPressed() {
        val intent = Intent()
        intent.putExtra("updateMoney", money)
        setResult(Activity.RESULT_OK, intent)
        super.onBackPressed()
    }



    fun switchList(type: Int){
        when(type){
            1->{
                if (mode == false) {
                    listAdapter(ShopList.list1)
                }
                else{
                    gridAdapter(ShopList.list1)

                }
            }
            2->{
                if (mode == false) {
                    listAdapter(ShopList.list2)
                }
                else{
                    gridAdapter(ShopList.list2)
                }
            }
            3->{
                if (mode == false) {
                    listAdapter(ShopList.list3)
                }
                else{
                    gridAdapter(ShopList.list3)
                }
            }
        }
    }
    fun listAdapter(list: ArrayList<MagicItem>){
        rv_shop.layoutManager = LinearLayoutManager(this)
        rv_shop.adapter = shopAdapter1
        shopAdapter1.setToClick(object : ShopAdapter_list.ItemClickListener{
            override fun toClick(item: MagicItem) {
                purchaseDialog(item)
            }
        })
        shopAdapter1.update(list)

    }
    fun gridAdapter(list: ArrayList<MagicItem>){
        rv_shop.layoutManager = GridLayoutManager(this, 4)
        rv_shop.adapter = shopAdapter2
        shopAdapter2.setToClick(object : ShopAdapter_grid.ItemClickListener{
            override fun toClick(item: MagicItem) {
                purchaseDialog(item)
            }
        })
        shopAdapter2.update(list)
    }

    fun checkType(type: Int){
        checkList.clear()
        checkList.add(type)
    }
    fun changeType(){
        if (checkList[0] == 1){
            switchList(1)
        } else if (checkList[0] == 2){
            switchList(2)
        }else{
           switchList(3)
        }
    }
    fun purchaseDialog(item: MagicItem){
        val inflater = LayoutInflater.from(this)
        val view = inflater.inflate(R.layout.dialog_view, null)
        val dialog_purchase = AlertDialog.Builder(this)
            .setView(view)
            .show()
        val image = view.findViewById<ImageView>(R.id.dialog_image)
        val name = view.findViewById<TextView>(R.id.dialog_name)
        val price = view.findViewById<TextView>(R.id.dialog_price)
        val btn_confirm = view.findViewById<Button>(R.id.btn_confirm)
        val btn_cancel = view.findViewById<Button>(R.id.btn_cancel)

        image.setImageResource(item.image)
        name.setText(item.name)
        price.setText("$ ${item.price}")
        btn_confirm.setOnClickListener{
            if (money >= item.price){
                money = money - item.price
                tv_money.setText("$money")     //錢幣減少

                item.isPurchased = true        //更改為已購買狀態
                updateList()

                savePurchase(item)             //儲存進自己的魔法庫

                dialog_purchase.dismiss()
            }
            else{
                dialog_purchase.dismiss()
                val view = LayoutInflater.from(this).inflate(R.layout.dialog_laugh, null)
                val dialog_laugh = AlertDialog.Builder(this)
                    .setView(view)
                    .show()
                val btn_bye = view.findViewById<Button>(R.id.btn_bye)
                btn_bye.setOnClickListener(object : View.OnClickListener{
                    override fun onClick(p0: View?) {
                        dialog_laugh.dismiss()
                    }
                })

            }
        }
        btn_cancel.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                dialog_purchase.dismiss()
            }
        })

    }
    fun updateList(){
        shopAdapter1.notifyDataSetChanged()
        shopAdapter2.notifyDataSetChanged()
    }

    fun savePurchase(item: MagicItem){
        if (item.level == 1){
            storage(storage1, item)
        }
        else if (item.level == 2){
            storage(storage2, item)
        }
        else{
            storage(storage3, item)
        }
    }

    fun storage(storage: SharedPreferences, item: MagicItem){
        val editor = storage.edit()
        val itemJson = Gson().toJson(item)
        var i = 0
        while (!storage.getString("$i", "").isNullOrEmpty()) {
            i++
        }
        editor.putString("$i", itemJson).apply()
    }
}
