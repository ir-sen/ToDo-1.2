package com.example.todo_12.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.todo_12.R
import com.example.todo_12.databinding.ActivityShopItemBinding
import com.example.todo_12.domain.ShopItem

class ShopItemActivity : AppCompatActivity(), ShopItemFragment.OnEditingFinishedListener {

    lateinit var binding: ActivityShopItemBinding

    private var screenMode = MODE_UNKNOWN
    private var shopItemId = ShopItem.UNDEFIND_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        parseIntent()
        if (savedInstanceState == null) {
            launchRightMode()
        }
    }

    private fun launchRightMode() {
        val fragment = when(screenMode) {
            EDIT_MODE -> ShopItemFragment.newInstanceEditItem(shopItemId)
            ADD_MODE -> ShopItemFragment.newInstanceAddItem()
            else -> throw RuntimeException("Parse key mode absent")
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.shop_item_container, fragment)
            .commit()
    }

// проверка интента на правельность
    private fun parseIntent() {
        if(!intent.hasExtra(EXTRA_KEY)) {
            throw RuntimeException("Parse key mode absent")
        }
        val mode = intent.getStringExtra(EXTRA_KEY)
        if (mode != ADD_MODE && mode != EDIT_MODE) {
            throw RuntimeException("Unknown extra mode $mode")
        }
        screenMode = mode
        if (screenMode == EDIT_MODE) {
            if (!intent.hasExtra(EXTRA_KEY_ITEM_ID)) {
                throw RuntimeException("Params shop item id is absent")
            }
            shopItemId = intent.getIntExtra(EXTRA_KEY_ITEM_ID, ShopItem.UNDEFIND_ID)
        }

    }

    override fun onEditingFinished() {
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
        finish()
    }

    companion object {
        private const val EXTRA_KEY = "extra_mode"
        private const val EDIT_MODE = "edit_mode"
        private const val ADD_MODE = "add_mode"
        private const val EXTRA_KEY_ITEM_ID = "item_id"
        private const val MODE_UNKNOWN = ""

         fun newIntentAddItem(context: Context): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_KEY, ADD_MODE)
            return intent
        }

        fun newIntentEditItem(context: Context, id: Int): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_KEY, EDIT_MODE)
            intent.putExtra(EXTRA_KEY_ITEM_ID, id)
            Log.d("ShopItemActivity", id.toString())
            return intent
        }

    }
}