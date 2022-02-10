package com.example.todo_12.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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


    suspend fun deleteItem(item: ShopItem) {
        viewModelScope.launch {
            deleteItemUseCase.deleteItem(item)
        }
    }

    fun changeEnableState(item: ShopItem) {
        viewModelScope.launch {
            val newItem = item.copy(enable = !item.enable)
            editItemUseCase.editItem(newItem)
        }
    }

}