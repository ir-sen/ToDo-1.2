package com.example.todo_12.data

import com.example.todo_12.domain.ShopItem
import com.example.todo_12.domain.ShopListRepository

object RepositoryImpl: ShopListRepository {

    private val shopList = mutableListOf<ShopItem>()

    private var autoIncrementId = 0

    override fun addItem(item: ShopItem) {
        if(item.id == ShopItem.UNDEFIND_ID) {
            item.id = autoIncrementId++
        }
        shopList.add(item)
    }

    override fun deleteItem(item: ShopItem) {
        shopList.remove(item)
    }

    override fun editItem(item: ShopItem) {
        val oldElement = getItem(item.id)
        shopList.remove(oldElement)
        addItem(item)
    }

    override fun getItem(id: Int): ShopItem {
        return shopList.find {
            it.id == id
        } ?: throw RuntimeException("Element with id $id not found")
    }

    override fun getShopList(): List<ShopItem> {
        return shopList.toList()
    }


}