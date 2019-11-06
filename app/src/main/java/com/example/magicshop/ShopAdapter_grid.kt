package com.example.magicshop

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ShopAdapter_grid : RecyclerView.Adapter<ShopAdapter_grid.ViewHolder>() {
    private val magic_list: ArrayList<MagicItem> = arrayListOf()
    private var buyListener : ItemClickListener? = null

    interface ItemClickListener{
        fun toClick(item: MagicItem)
    }
    fun setToClick(listener: ItemClickListener){
        buyListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.grid_itemview, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return magic_list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(magic_list[position])
    }
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val imageView = view.findViewById<ImageView>(R.id.img_magic2)
        val soldou2 = view.findViewById<TextView>(R.id.tv_soldout2)

        fun bind(item: MagicItem){
            imageView.setImageResource(item.image)
            itemView.setOnClickListener{
                buyListener?.toClick(item)
            }
            if (item.isPurchased == true){
                soldou2.visibility = View.VISIBLE
                itemView.isEnabled = false
            }else{
                soldou2.visibility = View.INVISIBLE
                itemView.isEnabled = true

            }
            itemView.setOnClickListener{
                buyListener?.toClick(item)
            }
        }
    }
    fun update(newList: ArrayList<MagicItem>){
        magic_list.clear()
        magic_list.addAll(newList)
        notifyDataSetChanged()
    }
}
