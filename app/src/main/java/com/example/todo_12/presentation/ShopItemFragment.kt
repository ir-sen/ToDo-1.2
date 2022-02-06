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

class ShopItemFragment: Fragment() {

    private lateinit var viewModel: ItemViewModel

    private var screenMode = MODE_UNKNOWN
    private var shopItemId = ShopItem.UNDEFIND_ID

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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParams()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
            activity?.onBackPressed()
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
        val args = requireArguments()
        if(!args.containsKey(EXTRA_KEY)) {
            throw RuntimeException("Parse key mode absent")
        }
        val mode = args.getString(EXTRA_KEY)
        if (mode != ADD_MODE && mode != EDIT_MODE) {
            throw RuntimeException("Unknown extra mode $mode")
        }
        screenMode = mode
        if (screenMode == EDIT_MODE) {
            if (!args.containsKey(EXTRA_KEY_ITEM_ID)) {
                throw RuntimeException("Params shop item id is absent")
            }
            shopItemId = args.getInt(EXTRA_KEY_ITEM_ID, ShopItem.UNDEFIND_ID)
        }
    }

    companion object {
        private const val EXTRA_KEY = "extra_mode"
        private const val EDIT_MODE = "edit_mode"
        private const val ADD_MODE = "add_mode"
        private const val EXTRA_KEY_ITEM_ID = "item_id"
        private const val MODE_UNKNOWN = ""

        fun newInstanceAddItem(): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_KEY, ADD_MODE)
                }
            }
        }

        fun newInstanceEditItem(shopItemId: Int): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_KEY, EDIT_MODE)
                    putInt(EXTRA_KEY_ITEM_ID, shopItemId)
                }
            }
        }

    }


}