package com.example.todo_12.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todo_12.data.RepositoryImpl
import com.example.todo_12.domain.AddItemUseCase
import com.example.todo_12.domain.EditItemUseCase
import com.example.todo_12.domain.GetItemUseCase
import com.example.todo_12.domain.ShopItem

class ItemViewModel: ViewModel() {

    private val repository = RepositoryImpl

    private val addItemUseCase = AddItemUseCase(repository)
    private val getItemUseCase = GetItemUseCase(repository)
    private val editItemUseCase = EditItemUseCase(repository)

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount: LiveData<Boolean>
        get() = _errorInputCount


    fun addItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val validate = validateInput(name, count)
        if (validate) {
            val shopItem = ShopItem(name, count, true)
            addItemUseCase.addItem(shopItem)
        }
    }

    fun getItem(id: Int) {
        val item = getItemUseCase.getItem(id)
    }

    fun editItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val validate = validateInput(name, count)
        if (validate) {
            val ShopItem = ShopItem(name, count, true)
            editItemUseCase.editItem(ShopItem)
        }
    }

    private fun parseName(name: String?): String {
        return name?.trim() ?: ""
    }

    private fun parseCount(count: String?): Int {
        return try {
            count?.trim()?.toInt() ?: NULL
        } catch (e: Exception) {
            NULL
        }
    }

    private fun validateInput(inputName: String, inputCount: Int): Boolean {
        var result = true
        if (inputName.isBlank()) {
            TODO("show error input name")
            _errorInputName.value = true
            result = false
        }

        if (inputCount < 0) {
            TODO("show error input count")
            _errorInputCount.value = true
            result = false
        }
        return result
    }

    fun resetErrorInputName() {
        _errorInputName.value = false
    }

    fun resetErrorCount() {
        _errorInputCount.value = false
    }

    companion object {
        const val NULL = 0
    }
}

