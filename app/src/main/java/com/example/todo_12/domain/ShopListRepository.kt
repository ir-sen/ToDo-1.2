package com.example.todo_12.domain

import androidx.lifecycle.LiveData

interface ShopListRepository {

    suspend fun addItem(item: ShopItem)

    suspend fun deleteItem(item: ShopItem)

    suspend fun editItem(item: ShopItem)

    suspend fun getItem(id: Int): ShopItem

    fun getShopList(): LiveData<List<ShopItem>>

}