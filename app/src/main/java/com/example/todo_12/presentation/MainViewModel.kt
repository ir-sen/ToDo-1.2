package com.example.todo_12.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.example.todo_12.data.RepositoryImpl
import com.example.todo_12.domain.DeleteItemUseCase
import com.example.todo_12.domain.EditItemUseCase
import com.example.todo_12.domain.GetShopListUseCase
import com.example.todo_12.domain.ShopItem


class MainViewModel: ViewModel() {
    private val repository = RepositoryImpl

    private val getShopListUseCase = GetShopListUseCase(repository)
    private val deleteItemUseCase = DeleteItemUseCase(repository)
    private val editItemUseCase = EditItemUseCase(repository)

    val shopList = getShopListUseCase.getShopList()


    fun deleteItem(item: ShopItem) {
        deleteItemUseCase.deleteItem(item)
    }

    fun changeEnableState(item: ShopItem) {
        val newItem = item.copy(enable = !item.enable)
        editItemUseCase.editItem(newItem)
    }

}