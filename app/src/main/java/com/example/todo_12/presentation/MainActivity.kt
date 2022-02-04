package com.example.todo_12.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.todo_12.R


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
            adapterShL.submitList(it)
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

        setUpClickListener()
        setUpLongClickListener()
        setUpSwipeListener(rvShopList)

    }

    private fun setUpSwipeListener(rvShopList: RecyclerView) {
        val swipeCallback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val currentPosition = adapterShL.currentList[viewHolder.adapterPosition]
                viewModel.deleteItem(currentPosition)
            }

        }
        val itemTouchHelper = ItemTouchHelper(swipeCallback)
        itemTouchHelper.attachToRecyclerView(rvShopList)
    }

    private fun setUpLongClickListener() {
        adapterShL.onItemLongListener = {
            Log.d("OnClickListener", "$it")
        }
    }

    private fun setUpClickListener() {
        adapterShL.onShopItemLongClickListener = {
            viewModel.changeEnableState(it)
        }
    }
}
