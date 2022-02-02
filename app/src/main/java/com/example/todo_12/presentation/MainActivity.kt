package com.example.todo_12.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.todo_12.R
import com.example.todo_12.domain.ShopItem
import java.security.Provider

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var listLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        listLayout = findViewById(R.id.llShopList)
        // subscribe shop list
        viewModel.shopList.observe(this) {
            showList(it)
        }
    }

    private fun showList(list: List<ShopItem>) {
        listLayout.removeAllViews()
        for (shopItem in list) {
            val shopLayout = if (shopItem.enable) {
                R.layout.item_shop_enable
            } else {
                R.layout.item_shop_disable
            }

            val view = LayoutInflater.from(this).inflate(
                shopLayout,
                listLayout,
                false
            )
            val nameText = view.findViewById<TextView>(R.id.tv_name)
            val countText = view.findViewById<TextView>(R.id.tv_count)
            nameText.text = shopItem.name
            countText.text = shopItem.count.toString()
            view.setOnLongClickListener {
                viewModel.changeEnableState(shopItem)
                true
            }
            listLayout.addView(view)
        }

    }


}