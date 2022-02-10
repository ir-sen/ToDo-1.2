package com.example.todo_12.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.todo_12.data.RepositoryImpl
import com.example.todo_12.domain.DeleteItemUseCase
import com.example.todo_12.domain.EditItemUseCase
import com.example.todo_12.domain.GetShopListUseCase
import com.example.todo_12.domain.ShopItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MainViewModel(application: Application): AndroidViewModel(application) {

    private val repository = RepositoryImpl(application)
    private val getShopListUseCase = GetShopListUseCase(repository)
    private val deleteItemUseCase = DeleteItemUseCase(repository)
    private val editItemUseCase = EditItemUseCase(repository)

    val shopList = getShopListUseCase.getShopList()
    private var scope = CoroutineScope(Dispatchers.IO)

    suspend fun deleteItem(item: ShopItem) {
        scope.launch {
            deleteItemUseCase.deleteItem(item)
        }
    }

    fun changeEnableState(item: ShopItem) {
        scope.launch {
            val newItem = item.copy(enable = !item.enable)
            editItemUseCase.editItem(newItem)
        }
    }

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }
}