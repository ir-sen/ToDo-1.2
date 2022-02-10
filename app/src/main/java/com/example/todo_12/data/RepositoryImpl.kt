package com.example.todo_12.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.todo_12.domain.ShopItem
import com.example.todo_12.domain.ShopListRepository
import kotlin.random.Random

class RepositoryImpl(
    application: Application
): ShopListRepository {

    private val shopListDb = AppDataBase.getInstance(application).shopListDao()
    private val mapper = ShopListMapper()

    override fun addItem(item: ShopItem) {
        shopListDb.addShopItem(mapper.mapEntityToDbModel(item))
    }

    override fun deleteItem(item: ShopItem) {
        shopListDb.deleteSelectItem(item.id)
    }

    override fun editItem(item: ShopItem) {
        shopListDb.addShopItem(mapper.mapEntityToDbModel(item))
    }

    override fun getItem(id: Int): ShopItem {
        val dbModel = shopListDb.getShopItem(id)
        return mapper.mapDbModelToEntity(dbModel)
    }

    override fun getShopList(): LiveData<List<ShopItem>> = MediatorLiveData<List<ShopItem>>().apply {
        addSource(shopListDb.getList()) {
            value = mapper.mapListDbModelToListEntity(it)
        }
    }
}