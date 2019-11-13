package com.example.magicshop

import android.app.Activity
import android.content.Intent
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
import kotlinx.android.synthetic.main.activity_shop.*
import kotlinx.android.synthetic.main.activity_shop.tv_usermoney
import kotlinx.android.synthetic.main.fragment_userpage.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShopActivity : AppCompatActivity() {
    private var money: Int = 0
    private val shopAdapter1 = ShopAdapter_list()
    private val shopAdapter2 = ShopAdapter_grid()
    private var mode = false
    private var checkList = arrayListOf(1)
    lateinit var level1_list : MutableList<MagicItems>
    lateinit var level2_list : MutableList<MagicItems>
    lateinit var level3_list : MutableList<MagicItems>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop)

        money = intent.getIntExtra("money", 0)
        tv_usermoney.setText("$money")

        Api.service.showMagic().enqueue(object: Callback<MagicList>{
            override fun onResponse(call: Call<MagicList>, response: Response<MagicList>) {
                val magicList = response.body()!!.data
                level1_list = magicList.filter { it.level == "one" }.toMutableList()
                level2_list = magicList.filter { it.level == "two" }.toMutableList()
                level3_list = magicList.filter { it.level == "three" }.toMutableList()

                println("================$level3_list")

                println("================beforeSWITCH")
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
            override fun onFailure(call: Call<MagicList>, t: Throwable) {
                println("=============$t")
            }
        })

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
                    listAdapter(level1_list)
                }
                else{
                    gridAdapter(ShopList.list1)

                }
            }
            2->{
                if (mode == false) {
                    listAdapter(level2_list)
                }
                else{
                    gridAdapter(ShopList.list2)
                }
            }
            3->{
                if (mode == false) {
                    listAdapter(level3_list)
                }
                else{
                    gridAdapter(ShopList.list3)
                }
            }
        }
    }
    fun listAdapter(list: MutableList<MagicItems>){
        rv_shop.layoutManager = LinearLayoutManager(this)
        rv_shop.adapter = shopAdapter1

        //添加分隔線
//        val divider = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
//        rv_shop.addItemDecoration(divider) purchaseDialog(item)

        shopAdapter1.setToClick(object : ShopAdapter_list.ItemClickListener{
            override fun toClick(item: MagicItems) {
//                purchaseDialog(item)
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
                tv_usermoney.setText("$money")     //錢幣減少

                item.isPurchased = true        //更改為已購買狀態
                updateList()

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

}
