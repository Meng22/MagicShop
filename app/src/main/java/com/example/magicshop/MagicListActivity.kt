package com.example.magicshop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_magic_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MagicListActivity : AppCompatActivity() {
    private val level1_Adapter = LevelOne_Adapter()
    private val level2_Adapter = LevelTwo_Adapter()
    private val level3_Adapter = LevelThree_Adapter()
    lateinit var level1_list : MutableList<MagicItems>
    lateinit var level2_list : MutableList<MagicItems>
    lateinit var level3_list : MutableList<MagicItems>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_magic_list)
        Api.service.showMagic().enqueue(object: Callback<MagicList> {
            override fun onFailure(call: Call<MagicList>, t: Throwable) {
                println("=============$t")
            }
            override fun onResponse(call: Call<MagicList>, response: Response<MagicList>) {
                val magicList = response.body()!!.data
                level1_list = magicList.filter { it.level == "one" }.toMutableList()
                level2_list = magicList.filter { it.level == "two" }.toMutableList()
                level3_list = magicList.filter { it.level == "three" }.toMutableList()

                rv_level1.layoutManager = GridLayoutManager(this@MagicListActivity,4)
                rv_level1.adapter = level1_Adapter
                level1_Adapter.update(level1_list)

                rv_level2.layoutManager = GridLayoutManager(this@MagicListActivity,4)
                rv_level2.adapter = level2_Adapter
                level2_Adapter.update(ShopList.list2)

                rv_level3.layoutManager = GridLayoutManager(this@MagicListActivity,4)
                rv_level3.adapter = level3_Adapter
                level3_Adapter.update(ShopList.list3)

            }
        })

    }

}
