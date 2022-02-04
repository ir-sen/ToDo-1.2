package com.example.todo_12.presentation

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todo_12.R

class ShopItemViewHolder(val view: View): RecyclerView.ViewHolder(view) {
    val nameText = view.findViewById<TextView>(R.id.tv_name)
    val countText = view.findViewById<TextView>(R.id.tv_count)
}