package com.example.todo_12.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.todo_12.R
import com.example.todo_12.domain.ShopItem

class ShopListAdapter: RecyclerView.Adapter<ShopListAdapter.ShopItemViewHolder>() {

    var shopList = listOf<ShopItem>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_shop_enable,
            parent,
            false
        )
        return ShopItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val shopItem = shopList[position]
        var status = if(shopItem.enable) {
            "Active"
        } else{
            "No Active"
        }

        holder.view.setOnLongClickListener {
            Toast.makeText(holder.view.context, "This is long", Toast.LENGTH_SHORT).show()
            true
        }
        if (shopItem.enable) {
            holder.nameText.text = "${shopItem.name} $status"
            holder.countText.text = shopItem.count.toString()
            holder.nameText.setTextColor(
                ContextCompat.getColor(holder.view.context, android.R.color.holo_red_dark)
            )
        } 
//        } else {
//            holder.nameText.text = ""
//            holder.countText.text = ""
//            holder.nameText.setTextColor(
//                ContextCompat.getColor(holder.view.context, android.R.color.white)
//            )
//        }
    }

    override fun onViewRecycled(holder: ShopItemViewHolder) {
        super.onViewRecycled(holder)
        holder.nameText.text = ""
        holder.countText.text = ""
        holder.nameText.setTextColor(
            ContextCompat.getColor(holder.view.context, android.R.color.white)
        )
    }

    override fun getItemCount(): Int {
        return shopList.size
    }


    class ShopItemViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        val nameText = view.findViewById<TextView>(R.id.tv_name)
        val countText = view.findViewById<TextView>(R.id.tv_count)
    }
}