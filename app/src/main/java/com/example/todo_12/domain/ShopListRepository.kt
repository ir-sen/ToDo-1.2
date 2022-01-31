package com.example.todo_12.domain

interface ShopListRepository {

    fun addItem(itme: ShopItem)

    fun deleteItem(item: ShopItem)

    fun editItem(item: ShopItem)

    fun getItem(id: Int): ShopItem

    fun getShopList(): List<ShopItem>

}