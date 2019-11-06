package com.example.magicshop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_magic_list.*

class MagicListActivity : AppCompatActivity() {
    private val level1_Adapter = LevelOne_Adapter()
    private val level2_Adapter = LevelTwo_Adapter()
    private val level3_Adapter = LevelThree_Adapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_magic_list)

        rv_level1.layoutManager = GridLayoutManager(this,4)
        rv_level1.adapter = level1_Adapter
        level1_Adapter.update(ShopList.list1)

        rv_level2.layoutManager = GridLayoutManager(this,4)
        rv_level2.adapter = level2_Adapter
        level2_Adapter.update(ShopList.list2)

        rv_level3.layoutManager = GridLayoutManager(this,4)
        rv_level3.adapter = level3_Adapter
        level3_Adapter.update(ShopList.list3)
    }

}
