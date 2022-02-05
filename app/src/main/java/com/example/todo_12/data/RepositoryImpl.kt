package com.example.todo_12.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.todo_12.domain.ShopItem
import com.example.todo_12.domain.ShopListRepository
import kotlin.random.Random

object RepositoryImpl: ShopListRepository {

    private val shopList = sortedSetOf<ShopItem>({o1, o2 -> o1.id.compareTo(o2.id)})
    private val shopListDb = MutableLiveData<List<ShopItem>>()

    private var autoIncrementId = 0

    init {
        for (i in 0..10) {
            var item = ShopItem("Name $i", i, Random.nextBoolean())
            addItem(item)
        }
    }

    override fun addItem(item: ShopItem) {
        if(item.id == ShopItem.UNDEFIND_ID) {
            item.id = autoIncrementId++
        }
        shopList.add(item)
        updateList()
    }

    override fun deleteItem(item: ShopItem) {
        shopList.remove(item)
        updateList()
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

    override fun getShopList(): LiveData<List<ShopItem>> {
        return shopListDb
    }

    private fun updateList() {
        shopListDb.value = shopList.toList()
    }


}