package com.example.todo_12.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.todo_12.domain.ShopItem
import com.example.todo_12.domain.ShopListRepository
import javax.inject.Inject

// реализация функций через базу данных
class RepositoryImpl @Inject constructor(
    private val mapper: ShopListMapper,
    private val shopListDao: ShopListDao,
): ShopListRepository {

//    private val shopListDb = AppDataBase.getInstance(application).shopListDao()


    override suspend fun addItem(item: ShopItem) {
        shopListDao.addShopItem(mapper.mapEntityToDbModel(item))
    }

    override suspend fun deleteItem(item: ShopItem) {
        shopListDao.deleteSelectItem(item.id)
    }

    override suspend fun editItem(item: ShopItem) {
        shopListDao.addShopItem(mapper.mapEntityToDbModel(item))
    }

    override suspend fun getItem(id: Int): ShopItem {
        val dbModel = shopListDao.getShopItem(id)
        return mapper.mapDbModelToEntity(dbModel)
    }

    override fun getShopList(): LiveData<List<ShopItem>> = MediatorLiveData<List<ShopItem>>().apply {
        addSource(shopListDao.getList()) {
            value = mapper.mapListDbModelToListEntity(it)
        }
    }
}