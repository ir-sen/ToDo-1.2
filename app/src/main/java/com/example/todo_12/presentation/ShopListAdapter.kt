package com.example.todo_12.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.todo_12.R
import com.example.todo_12.domain.ShopItem

class ShopListAdapter: RecyclerView.Adapter<ShopListAdapter.ShopItemViewHolder>() {

    private var count = 0
    var onShopItemLongClickListere: OnShopItemLongClickListener? = null
    var shopList = listOf<ShopItem>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }
// viewType = override fun getItemViewType
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        Log.d("ShopListAdapter", "onCreateViewHolder, count: ${++count}")
        val layout = when(viewType) {
            ENABLE -> R.layout.item_shop_enable
            DISABLE -> R.layout.item_shop_disable
            else -> throw RuntimeException("Unknown viewType: $viewType")
        }

        val view = LayoutInflater.from(parent.context).inflate(
            layout,
            parent,
            false
        )
        return ShopItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val shopItem = shopList[position]

        holder.view.setOnLongClickListener {
            Toast.makeText(holder.view.context, "This is long", Toast.LENGTH_SHORT).show()
            onShopItemLongClickListere?.longListener(shopItem)

            true
        }

        holder.nameText.text = shopItem.name
        holder.countText.text = shopItem.count.toString()
//            holder.nameText.setTextColor(
//                ContextCompat.getColor(holder.view.context, android.R.color.holo_red_dark)
//            )
//        }else {
//            holder.nameText.text = shopItem.name
//            holder.countText.text = shopItem.name
//        }
////            holder.nameText.setTextColor(
////                ContextCompat.getColor(holder.view.context, android.R.color.white)
////            )
////        }
    }


    override fun getItemCount(): Int {
        return shopList.size
    }
// this is viewType in onCreateViewHolder
    override fun getItemViewType(position: Int): Int {
        val item = shopList[position]
        return if (item.enable) {
            ENABLE
        } else {
            DISABLE
        }

    }


    class ShopItemViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        val nameText = view.findViewById<TextView>(R.id.tv_name)
        val countText = view.findViewById<TextView>(R.id.tv_count)
    }

    interface OnShopItemLongClickListener {
        fun longListener(shopItem: ShopItem)
    }
    companion object {
        const val ENABLE = 1
        const val DISABLE = 0

        const val MAX_POOL_SIZE = 30
    }
}
