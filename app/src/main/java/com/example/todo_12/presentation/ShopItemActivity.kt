package com.example.todo_12.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.todo_12.R

class ShopItemActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)
        val mode = intent.getStringExtra("extra_mode")
        Log.d("ShopItemActivity", mode.toString())
    }

    companion object {
        private const val EXTRA_KEY = "extra_mode"
        private const val EDIT_MODE = "edit_mode"
        private const val ADD_MODE = "add_mode"
        private const val EXTRA_KEY_ITEM_ID = "item_id"

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