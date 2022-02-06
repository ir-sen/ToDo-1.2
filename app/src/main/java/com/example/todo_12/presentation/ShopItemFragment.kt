package com.example.todo_12.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.todo_12.R
import com.example.todo_12.databinding.FragmentShopItemBinding
import com.example.todo_12.domain.ShopItem

class ShopItemFragment(
    private val screenMode: String = MODE_UNKNOWN,
    private val shopItemId: Int = ShopItem.UNDEFIND_ID
): Fragment() {

    private lateinit var viewModel: ItemViewModel



    private var _binding: FragmentShopItemBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShopItemBinding.inflate(inflater, container, false)
        //val binding: FragmentShopItemBinding = FragmentShopItemBinding.inflate(inflater, R.layout.fragment_shop_item, container, false)
        return binding.root
//        return inflater.inflate(R.layout.fragment_shop_item, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parseParams()
        viewModel = ViewModelProvider(this)[ItemViewModel::class.java]
        addTextChangeListeners()
        launchRightMode()
        observeViewModel()
    }


        private fun observeViewModel() {
        // set  error in count view
        viewModel.errorInputCount.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.count_error)
            } else {
                null
            }
            binding.tilCount.error = message
        }

        viewModel.errorInputName.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.name_error)
            } else {
                null
            }
            binding.tilName.error = message
        }
        viewModel.enableClose.observe(viewLifecycleOwner) {
            finish()
        }
    }

    private fun launchRightMode() {
        when(screenMode) {
            EDIT_MODE -> launchEditMode()
            ADD_MODE -> launchAddMode()
        }
    }

    private fun addTextChangeListeners() {
        // hide error when user input text
        binding.edName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputName()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        binding.edCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorCount()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun launchEditMode() {
        // get current item
        viewModel.getItem(shopItemId)
        // set listener
        viewModel.shopItem.observe(viewLifecycleOwner){
            // set value what you write
            with(binding) {
                edName.setText(it.name)
                edCount.setText(it.count.toString())
            }
        }
        with(binding) {
            saveButton.setOnClickListener {
                viewModel.editItem(edName.text?.toString(), edCount.text?.toString())
            }
        }
    }

    private fun launchAddMode() {
        with(binding) {
            saveButton.setOnClickListener {
                viewModel.addItem(edName.text?.toString(), edCount.text?.toString())
            }
        }

    }

// проверка интента на правельность
    private fun parseParams() {
        if (screenMode != EDIT_MODE && screenMode != ADD_MODE) {
            throw RuntimeException("Parse key mode absent")
        }
        if (screenMode == EDIT_MODE && shopItemId == ShopItem.UNDEFIND_ID) {
            throw RuntimeException("Params shop item id is absent")
        }
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