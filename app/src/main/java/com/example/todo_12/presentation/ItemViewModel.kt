package com.example.todo_12.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todo_12.data.RepositoryImpl
import com.example.todo_12.domain.AddItemUseCase
import com.example.todo_12.domain.EditItemUseCase
import com.example.todo_12.domain.GetItemUseCase
import com.example.todo_12.domain.ShopItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class ItemViewModel(application: Application): AndroidViewModel(application) {

    private val repository = RepositoryImpl(application)

    private val addItemUseCase = AddItemUseCase(repository)
    private val getItemUseCase = GetItemUseCase(repository)
    private val editItemUseCase = EditItemUseCase(repository)

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount: LiveData<Boolean>
        get() = _errorInputCount

    private val _shopItem = MutableLiveData<ShopItem>()
    val shopItem: LiveData<ShopItem>
        get() = _shopItem

    private val _enableClose = MutableLiveData<Unit>()
    val enableClose: LiveData<Unit>
        get() = _enableClose

    private val scope = CoroutineScope(Dispatchers.IO)

    fun addItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val validate = validateInput(name, count)
        if (validate) {
            scope.launch {
                val shopItem = ShopItem(name, count, true)
                addItemUseCase.addItem(shopItem)
                finishWork()
            }
        }
    }

    fun getItem(id: Int) {
        scope.launch {
            val item = getItemUseCase.getItem(id)
            _shopItem.value = item
        }
    }

    fun editItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val validate = validateInput(name, count)
        if (validate) {
            // вытаскиваем значения из LiveData на прямую (его копию) что бы изменить
            _shopItem.value?.let {
                scope.launch {
                    val item = it.copy(name = name, count = count)
                    editItemUseCase.editItem(item)
                    finishWork()
                }
            }
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
            _errorInputName.value = true
            result = false
        }

        if (inputCount <= 0) {
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

    private fun finishWork() {
        _enableClose.value = Unit
    }

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }

    companion object {
        const val NULL = 0
    }
}

