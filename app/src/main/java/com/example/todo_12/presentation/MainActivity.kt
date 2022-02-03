package com.example.todo_12.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.todo_12.R
import com.example.todo_12.domain.ShopItem

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapterShL: ShopListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpRecycleView()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        // subscribe shop list
        viewModel.shopList.observe(this) {
            adapterShL.shopList = it
        }
    }

    private fun setUpRecycleView() {
        val rvShopList = findViewById<RecyclerView>(R.id.rv_shop_list)
        with(rvShopList) {
            adapterShL = ShopListAdapter()
            adapter = adapterShL
            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.ENABLE,
                ShopListAdapter.MAX_POOL_SIZE
            )
            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.DISABLE,
                ShopListAdapter.MAX_POOL_SIZE
            )
        }

        adapterShL.onShopItemLongClickListener = {
            viewModel.changeEnableState(it)
        }

        adapterShL.onItemLongListener = {
            Log.d("OnClickListener", "$it")
        }




    }
}
