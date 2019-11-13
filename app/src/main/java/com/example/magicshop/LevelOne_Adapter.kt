package com.example.magicshop

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LevelOne_Adapter: RecyclerView.Adapter<LevelOne_Adapter.ViewHolder>() {
    private val magic_list: MutableList<MagicItems> = arrayListOf()

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

        fun bind(item: MagicItems){
//            image.setImageResource(item.image)

            if (item.store_id == null){
                shadow.visibility = View.INVISIBLE
            }else{
                shadow.visibility = View.VISIBLE
            }
        }
    }
    fun update(newList: MutableList<MagicItems>){
        magic_list.clear()
        magic_list.addAll(newList)
        notifyDataSetChanged()
    }
}