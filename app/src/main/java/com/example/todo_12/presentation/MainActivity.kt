package com.example.todo_12.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.todo_12.R
import com.example.todo_12.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject


class MainActivity : AppCompatActivity(), ShopItemFragment.OnEditingFinishedListener {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapterShL: ShopListAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private var shopItemContainer: FragmentContainerView? = null
    private val scope = CoroutineScope(Dispatchers.IO)

    lateinit var binding: ActivityMainBinding

    private val component by lazy {
        (application as ShopListApp).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        shopItemContainer = findViewById(R.id.shop_item_container)

        setUpRecycleView()
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        // subscribe shop list
        viewModel.shopList.observe(this) {
            adapterShL.submitList(it)
        }
// add button
        binding.buttonAddShopItem.setOnClickListener {
            if (isOnePaneMode()) {
                val intent = ShopItemActivity.newIntentAddItem(this)
                startActivity(intent)
            } else {
                launchFragment(ShopItemFragment.newInstanceAddItem())
            }
        }
    }
// realization
    override fun onEditingFinished() {
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
        supportFragmentManager.popBackStack()
    }

    private fun isOnePaneMode(): Boolean {
        return shopItemContainer == null
    }

    private fun launchFragment(fragment: Fragment){
        supportFragmentManager.popBackStack() // remove old fragment in backstack
        supportFragmentManager.beginTransaction()
            .replace(R.id.shop_item_container, fragment)
            .addToBackStack(null) // this fun add to fragment in backstack
            .commit()
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
        setUpLongClickListener()
        setUpClickListener()
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
                scope.launch {
                    viewModel.deleteItem(currentPosition)
                }
            }

        }
        val itemTouchHelper = ItemTouchHelper(swipeCallback)
        itemTouchHelper.attachToRecyclerView(rvShopList)
    }
    // edit
    private fun setUpClickListener() {
        adapterShL.onItemListener = {
            if (isOnePaneMode()) {
                val intentMy = ShopItemActivity.newIntentEditItem(this, it.id)
                startActivity(intentMy)
            } else {
                launchFragment(ShopItemFragment.newInstanceEditItem(it.id))
            }
        }
    }

    private fun setUpLongClickListener() {
        adapterShL.onShopItemLongClickListener = {
            viewModel.changeEnableState(it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }
}
