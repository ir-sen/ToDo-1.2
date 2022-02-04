package com.example.todo_12.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.todo_12.R
import com.example.todo_12.domain.ShopItem

class ShopListAdapter: RecyclerView.Adapter<ShopListAdapter.ShopItemViewHolder>() {

    private var count = 0
    var onShopItemLongClickListener: ((ShopItem) -> Unit)? = null
    var onItemLongListener: ((ShopItem) -> Unit)? = null


    var shopList = listOf<ShopItem>()
    set(value) {
        val callback = ShopListDiffCallback(shopList, value)
        val diffResult = DiffUtil.calculateDiff(callback)
        diffResult.dispatchUpdatesTo(this)
        field = value
    }

// viewType = override fun getItemViewType
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
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
        Log.d("onBindViewHolder", "onBindViewHolder, count: ${++count}")
        val shopItem = shopList[position]

        holder.view.setOnLongClickListener {
            Toast.makeText(holder.view.context, "This is long", Toast.LENGTH_SHORT).show()
            onShopItemLongClickListener?.invoke(shopItem)
            true
        }

        holder.view.setOnClickListener {
            onItemLongListener?.invoke(shopItem)
        }

        holder.nameText.text = shopItem.name
        holder.countText.text = shopItem.count.toString()
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


    companion object {
        const val ENABLE = 1
        const val DISABLE = 0

        const val MAX_POOL_SIZE = 30
    }
}
