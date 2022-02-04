package com.example.todo_12.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.ListAdapter
import com.example.todo_12.R
import com.example.todo_12.domain.ShopItem

class ShopListAdapter: ListAdapter<ShopItem, ShopItemViewHolder>(ShopItemDiffCallback()) {


    var onShopItemLongClickListener: ((ShopItem) -> Unit)? = null
    var onItemLongListener: ((ShopItem) -> Unit)? = null


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
        val shopItem = getItem(position)

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

// this is viewType in onCreateViewHolder
    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return if (item.enable) {
            ENABLE
        } else {
            DISABLE
        }

    }

    companion object {
        const val ENABLE = 1
        const val DISABLE = 0

        const val MAX_POOL_SIZE = 30
    }
}
