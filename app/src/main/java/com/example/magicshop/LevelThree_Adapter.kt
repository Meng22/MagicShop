package com.example.magicshop

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LevelThree_Adapter: RecyclerView.Adapter<LevelThree_Adapter.ViewHolder>() {
    private val magic_list: ArrayList<MagicItem> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.magiclist_itemview, parent, false)
        return ViewHolder(view)
    }
    override fun getItemCount(): Int {
        return magic_list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(magic_list[position])
    }
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val image = view.findViewById<ImageView>(R.id.magic_image)
        val shadow = view.findViewById<TextView>(R.id.magic_shadow)

        fun bind(item: MagicItem){
            image.setImageResource(item.image)

            if (item.isPurchased == true){
                shadow.visibility = View.INVISIBLE
            }else{
                shadow.visibility = View.VISIBLE
            }
        }
    }
    fun update(newList: ArrayList<MagicItem>){
        magic_list.clear()
        magic_list.addAll(newList)
        notifyDataSetChanged()
    }
}