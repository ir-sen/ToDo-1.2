package com.example.todo_12.presentation

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.net.Uri
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
import javax.inject.Inject
import kotlin.concurrent.thread

class ShopItemFragment: Fragment() {

    private lateinit var viewModel: ItemViewModel

    private var screenMode = MODE_UNKNOWN
    private var shopItemId = ShopItem.UNDEFIND_ID

    @Inject
    lateinit var viewModelFactory: ViewModelFactory


    lateinit var onEditingFinishedListener: OnEditingFinishedListener

    var count = 0

    private var _binding: FragmentShopItemBinding? = null
    private val binding get() = _binding!!

    private val component by lazy {
        (requireActivity().application as ShopListApp).component
    }

    override fun onAttach(activity: Activity) {
        component.inject(this)
        super.onAttach(activity)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        logP("onCreateView")
        _binding = FragmentShopItemBinding.inflate(inflater, container, false)
        //val binding: FragmentShopItemBinding = FragmentShopItemBinding.inflate(inflater, R.layout.fragment_shop_item, container, false)
        return binding.root
//        return inflater.inflate(R.layout., container, false)
    }

// Any Activity come in context
    // type conversion
    override fun onAttach(context: Context) {
        super.onAttach(context)
        logP("onAttach")
        // dose this method implement the interface we need ?
        if (context is OnEditingFinishedListener) {
            onEditingFinishedListener = context
        } else {
            throw RuntimeException("You haven't redefined the method interface onEditingFinishedListener")
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        logP("onCreate")
        super.onCreate(savedInstanceState)
        parseParams()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        logP("onViewCreated")
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[ItemViewModel::class.java]
        addTextChangeListeners()
        launchRightMode()
        observeViewModel()
    }


    override fun onDetach() {
        logP("onDestroyView")
        super.onDetach()
    }

    override fun onPause() {
        logP("onPause")
        super.onPause()
    }

    fun logP(value: String) {
        Log.d("FRAGMENT", "$value = ${++count}")
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
            onEditingFinishedListener.onEditingFinished()
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
//                viewModel.addItem(edName.text?.toString(), edCount.text?.toString())
                thread {
                    context?.contentResolver?.insert(
                        Uri.parse("content://com.example.todo_12/shop_items"),
                        ContentValues().apply {
                            put("id", 0)
                            put("name", binding.edName.text?.toString())
                            put("count", binding.edCount.text?.toString()?.toInt())
                            put("enabled", true)
                        }

                    )
                }
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

    interface OnEditingFinishedListener{
        fun onEditingFinished()
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